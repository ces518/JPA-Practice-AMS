package me.june.academy.domain.testType.web;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.Message;
import me.june.academy.domain.testType.TestType;
import me.june.academy.domain.testType.repository.TestTypeSearch;
import me.june.academy.domain.testType.service.TestTypeService;
import me.june.academy.domain.testType.validator.TestTypeValidator;
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
 * Date: 2020-03-25
 * Time: 01:43
 **/
@Controller
@RequestMapping("/testTypes")
@RequiredArgsConstructor
public class TestTypeController {
    private static final String TEST_TYPE_FORM = "testTypes/regist";
    private final TestTypeService testTypeService;

    @InitBinder("testTypeForm")
    public void testTypeValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new TestTypeValidator());
    }

    @GetMapping
    public String list(TestTypeSearch testTypeSearch,
                       Pageable pageable,
                       Model model) {
        Page<TestType> testTypePage = testTypeService.findAll(testTypeSearch, pageable);
        PageWrapper<TestType> page = new PageWrapper<>(testTypePage, "/testTypes");

        model.addAttribute("testTypePage", testTypePage);
        model.addAttribute("page", page);
        return "testTypes/list";
    }

    @GetMapping("new")
    public String form(Model model) {
        model.addAttribute("testTypeForm", new TestTypeForm());
        return TEST_TYPE_FORM;
    }

    @GetMapping("{id}")
    public String view(@PathVariable Long id, Model model) {
        TestType findTestType = testTypeService.findTestType(id);
        model.addAttribute("testType", findTestType);
        return "testTypes/view";
    }

    @GetMapping("{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        TestType findTestType = testTypeService.findTestType(id);
        model.addAttribute("testTypeForm", new TestTypeForm(findTestType));
        return TEST_TYPE_FORM;
    }

    @PostMapping("new")
    public String createTestType(@Valid @ModelAttribute TestTypeForm testTypeForm,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return TEST_TYPE_FORM;
        }
        Long savedTestTypeId = testTypeService.saveTestType(testTypeForm);
        redirectAttributes.addFlashAttribute("message", Message.CREATED.getMessage());
        return "redirect:/testTypes/" + savedTestTypeId;
    }

    @PostMapping("{id}/edit")
    public String updateTestType(@Valid @ModelAttribute TestTypeForm testTypeForm,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return TEST_TYPE_FORM;
        }
        testTypeService.updateTestType(testTypeForm);
        redirectAttributes.addFlashAttribute("message", Message.UPDATED.getMessage());
        return "redirect:/testTypes/" + testTypeForm.getId();
    }

    @DeleteMapping("{id}")
    public String deleteTestType(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        testTypeService.deleteTestType(id);
        redirectAttributes.addFlashAttribute("message", Message.DELETED.getMessage());
        return "redirect:/testTypes";
    }
}
