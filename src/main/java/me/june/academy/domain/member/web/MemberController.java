package me.june.academy.domain.member.web;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.Message;
import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-11
 * Time: 18:02
 **/
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final String MEMBER_FORM = "members/form";
    private final MemberService memberService;

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/list";
    }

    @GetMapping("/members/form")
    public String form(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return MEMBER_FORM;
    }

    @GetMapping("/members/{id}")
    public String view(@PathVariable Long id, Model model) {
        Member findMember = memberService.findMember(id);
        model.addAttribute("member", findMember);
        return "members/view";
    }

    @GetMapping("/members/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Member findMember = memberService.findMember(id);
        model.addAttribute("memberForm", new MemberForm(findMember));
        return MEMBER_FORM;
    }

    @PostMapping
    public String createMember(@ModelAttribute MemberForm memberForm,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return MEMBER_FORM;
        }
        Long savedId = memberService.saveMember(memberForm);
        redirectAttributes.addFlashAttribute("message", Message.CREATED.getMessage());
        return "redirect:/members/" + savedId;
    }

    @PostMapping("{id}/edit")
    public String updateMember(@ModelAttribute MemberForm memberForm,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return MEMBER_FORM;
        }
        memberService.updateMember(memberForm);
        redirectAttributes.addFlashAttribute("message", Message.UPDATED.getMessage());
        return "redirect:/members/" + memberForm.getId();
    }

    @DeleteMapping("{id}")
    public String deleteMember(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        memberService.deleteMember(id);
        redirectAttributes.addFlashAttribute("message", Message.DELETED.getMessage());
        return "redirect:/members";
    }
}
