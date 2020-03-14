package me.june.academy.domain.member.web;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.Message;
import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.repository.MemberSearch;
import me.june.academy.domain.member.service.MemberService;
import me.june.academy.domain.member.validator.MemberValidator;
import me.june.academy.utils.PageWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-11
 * Time: 18:02
 **/
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final String MEMBER_FORM = "members/regist";
    private final MemberService memberService;

    @InitBinder("memberForm")
    public void memberFormValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new MemberValidator());
    }

    @GetMapping
    public String list(Pageable pageable,
                       MemberSearch memberSearch,
                       Model model) {
        Page<Member> page = memberService.findAll(memberSearch, pageable);
        PageWrapper<Member> pageWrapper = new PageWrapper<>(page, "/members");

        model.addAttribute("members", page.getContent());
        model.addAttribute("page", pageWrapper);
        return "members/list";
    }

    @GetMapping("new")
    public String form(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return MEMBER_FORM;
    }

    @GetMapping("{id}")
    public String view(@PathVariable Long id,
                       Pageable pageable,
                       Model model) {
        Member findMember = memberService.findMember(id);
        model.addAttribute("member", findMember);
        return "members/view";
    }

    @GetMapping("{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Member findMember = memberService.findMember(id);
        model.addAttribute("memberForm", new MemberForm(findMember));
        return MEMBER_FORM;
    }

    @PostMapping("new")
    public String createMember(@Valid @ModelAttribute MemberForm memberForm,
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
    public String updateMember(@Valid @ModelAttribute MemberForm memberForm,
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
