package me.june.academy.domain.subject.web;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.Message;
import me.june.academy.domain.subject.Subject;
import me.june.academy.domain.subject.repository.SubjectSearch;
import me.june.academy.domain.subject.service.SubjectService;
import me.june.academy.utils.PageWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 22:33
 **/
@Controller
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final String SUBJECT_FORM = "subjects/regist";
    private final SubjectService subjectService;

    @GetMapping
    public String list(SubjectSearch subjectSearch,
                       Pageable pageable,
                       Model model) {
        Page<Subject> subjectPage = subjectService.findAll(subjectSearch, pageable);
        PageWrapper<Subject> page = new PageWrapper<>(subjectPage, "/subjects");

        model.addAttribute("subjectPage", subjectPage);
        model.addAttribute("page", page);
        return "subjects/list";
    }

    @GetMapping("new")
    public String form(Model model) {
        model.addAttribute("subjectForm", new SubjectForm());
        return SUBJECT_FORM;
    }

    @GetMapping("{id}")
    public String view(@PathVariable Long id, Model model) {
        Subject findSubject = subjectService.findSubject(id);
        model.addAttribute("subject", findSubject);
        return "subjects/view";
    }

    @GetMapping("{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Subject findSubject = subjectService.findSubject(id);
        model.addAttribute("subjectForm", new SubjectForm(findSubject));
        return SUBJECT_FORM;
    }

    @PostMapping("new")
    public String createSubject(@Valid @ModelAttribute SubjectForm subjectForm,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return SUBJECT_FORM;
        }
        Long savedSubjectId = subjectService.saveSubject(subjectForm);
        redirectAttributes.addFlashAttribute("message", Message.CREATED.getMessage());
        return "redirect:/subjects/" + savedSubjectId;
    }

    @PostMapping("{id}/edit")
    public String updateSubject(@Valid @ModelAttribute SubjectForm subjectForm,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return SUBJECT_FORM;
        }
        subjectService.updateSubject(subjectForm);
        redirectAttributes.addFlashAttribute("message", Message.UPDATED.getMessage());
        return "redirect:/subjects/" + subjectForm.getId();
    }

    @DeleteMapping("{id}")
    public String deleteSubject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        subjectService.deleteSubject(id);
        redirectAttributes.addFlashAttribute("message", Message.DELETED.getMessage());
        return "redirect:/subjects";
    }
}
