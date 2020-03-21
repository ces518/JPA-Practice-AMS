package me.june.academy.domain.grade.web;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.Message;
import me.june.academy.domain.grade.Grade;
import me.june.academy.domain.grade.repository.GradeSearch;
import me.june.academy.domain.grade.service.GradeService;
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
 * Date: 2020-03-21
 * Time: 23:35
 **/
@Controller
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {
    private static final String GRADE_FORM = "grades/regist";
    private final GradeService gradeService;

    @GetMapping
    public String list(GradeSearch gradeSearch,
                       Pageable pageable,
                       Model model) {
        Page<Grade> gradePage = gradeService.findAll(gradeSearch, pageable);
        PageWrapper page = new PageWrapper(gradePage, "/grades");

        model.addAttribute("gradePage", gradePage);
        model.addAttribute("page", page);
        return "grades/list";
    }

    @GetMapping("new")
    public String form(Model model) {
        model.addAttribute("gradeForm", new GradeForm());
        return GRADE_FORM;
    }

    @GetMapping("{id}")
    public String view(@PathVariable Long id, Model model) {
        Grade findGrade = gradeService.findGrade(id);
        model.addAttribute("grade", findGrade);
        return "grades/view";
    }

    @GetMapping("{id}/new")
    public String editForm(@PathVariable Long id, Model model) {
        Grade findGrade = gradeService.findGrade(id);
        model.addAttribute("gradeForm", new GradeForm(findGrade));
        return GRADE_FORM;
    }

    @PostMapping("new")
    public String createGrade(@Valid @ModelAttribute GradeForm gradeForm,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return GRADE_FORM;
        }
        Long savedGradeId = gradeService.saveGrade(gradeForm);
        redirectAttributes.addFlashAttribute("message", Message.CREATED.getMessage());
        return "redirect:/grades/" + savedGradeId;
    }

    @PostMapping("{id}/new")
    public String updateGrade(@Valid @ModelAttribute GradeForm gradeForm,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return GRADE_FORM;
        }
        gradeService.updateGrade(gradeForm);
        redirectAttributes.addFlashAttribute("message", Message.UPDATED.getMessage());
        return "redirect:/grades/" + gradeForm.getId();
    }

    @DeleteMapping("{id}")
    public String deleteGrade(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        gradeService.deleteGrade(id);
        redirectAttributes.addFlashAttribute("message", Message.DELETED.getMessage());
        return "redirect:/grades";
    }
}
