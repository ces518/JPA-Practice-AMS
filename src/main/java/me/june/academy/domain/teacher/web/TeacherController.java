package me.june.academy.domain.teacher.web;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.Message;
import me.june.academy.domain.teacher.Teacher;
import me.june.academy.domain.teacher.repository.TeacherSearch;
import me.june.academy.domain.teacher.service.TeacherService;
import me.june.academy.domain.teacher.validator.TeacherValidator;
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
 * Date: 2020-03-16
 * Time: 01:27
 **/
@Controller
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private static final String TEACHER_FORM = "teachers/regist";
    private final TeacherService teacherService;

    @InitBinder("teacherForm")
    public void teacherValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new TeacherValidator());
    }

    @GetMapping
    public String list(TeacherSearch teacherSearch,
                       Pageable pageable,
                       Model model) {
        Page<Teacher> teacherPage = teacherService.findAll(teacherSearch, pageable);
        PageWrapper<Teacher> page = new PageWrapper(teacherPage, "/teachers");

        model.addAttribute("teacherPage", teacherPage);
        model.addAttribute("page", page);
        return "teachers/list";
    }

    @GetMapping("new")
    public String form(Model model) {
        model.addAttribute("teacherForm", new TeacherForm());
        return TEACHER_FORM;
    }

    @GetMapping("{id}")
    public String view(@PathVariable Long id,
                       Model model) {
        Teacher findTeacher = teacherService.findTeacher(id);
        model.addAttribute("teacher", findTeacher);
        return "teachers/view";
    }

    @GetMapping("{id}/edit")
    public String editForm(@PathVariable Long id,
                           Model model) {
        Teacher findTeacher = teacherService.findTeacher(id);
        model.addAttribute("teacherForm", new TeacherForm(findTeacher));
        return TEACHER_FORM;
    }

    @PostMapping("new")
    public String createTeacher(@Valid @ModelAttribute TeacherForm teacherForm,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return TEACHER_FORM;
        }
        Long savedTeacherId = teacherService.saveTeacher(teacherForm);
        redirectAttributes.addFlashAttribute("message", Message.CREATED.getMessage());
        return "redirect:/teachers/" + savedTeacherId;
    }

    @PostMapping("{id}/edit")
    public String updateTeacher(@Valid @ModelAttribute TeacherForm teacherForm,
                       BindingResult result,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return TEACHER_FORM;
        }
        teacherService.updateTeacher(teacherForm);
        redirectAttributes.addFlashAttribute("message", Message.UPDATED.getMessage());
        return "redirect:/teachers/" + teacherForm.getId();
    }

    @DeleteMapping("{id}")
    public String deleteTeacher(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        teacherService.deleteTeacher(id);
        redirectAttributes.addFlashAttribute("message", Message.DELETED.getMessage());
        return "redirect:/teachers";
    }

    @ExceptionHandler(RuntimeException.class)
    public String errorPage(RuntimeException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errors/error";
    }
}
