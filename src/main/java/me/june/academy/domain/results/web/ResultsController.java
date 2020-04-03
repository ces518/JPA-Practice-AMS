package me.june.academy.domain.results.web;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.Message;
import me.june.academy.domain.member.repository.MemberRepository;
import me.june.academy.domain.results.Results;
import me.june.academy.domain.results.repository.ResultsSearch;
import me.june.academy.domain.results.repository.query.ResultsQueryDto;
import me.june.academy.domain.results.repository.query.ResultsQueryRepository;
import me.june.academy.domain.results.service.ResultsService;
import me.june.academy.domain.results.validator.ResultsCreateValidator;
import me.june.academy.domain.results.validator.ResultsUpdateValidator;
import me.june.academy.domain.subject.repository.SubjectRepository;
import me.june.academy.domain.testType.repository.TestTypeRepository;
import me.june.academy.model.Status;
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
 * Date: 2020-03-27
 * Time: 02:20
 **/
@Controller
@RequestMapping("/results")
@RequiredArgsConstructor
public class ResultsController {
    private final ResultsQueryRepository resultsQueryRepository;
    private final ResultsService resultsService;
    private final MemberRepository memberRepository;
    private final SubjectRepository subjectRepository;
    private final TestTypeRepository testTypeRepository;

    @InitBinder("createForm")
    public void resultsCreateValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ResultsCreateValidator());
    }

    @InitBinder("updateForm")
    public void resultsUpdateValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ResultsUpdateValidator());
    }

    @GetMapping
    public String list(ResultsSearch resultsSearch,
                       Pageable pageable,
                       Model model) {
        Page<ResultsQueryDto> resultsPage = resultsQueryRepository.findAll(resultsSearch, pageable);
        PageWrapper<ResultsQueryDto> page = new PageWrapper<>(resultsPage, "/results");

        model.addAttribute("resultsPage", resultsPage);
        model.addAttribute("page", page);
        return "results/list";
    }

    @GetMapping("new")
    public String form(Model model) {
        initValues(model);
        model.addAttribute("createForm", new ResultsForm.CreateRequest());
        return "results/createResultsForm";
    }

    @GetMapping("{id}")
    public String view(@PathVariable Long id, Model model) {
        Results findResults = resultsService.findResults(id);
        model.addAttribute("results", findResults);
        return "results/view";
    }

    @GetMapping("{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Results findResults = resultsService.findResults(id);
        initValues(model);
        model.addAttribute("results", findResults);
        model.addAttribute("updateForm", new ResultsForm.UpdateRequest(findResults));
        return "results/updateResultsForm";
    }

    @PostMapping("new")
    public String createResults(@Valid @ModelAttribute("createForm") ResultsForm.CreateRequest createRequest,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            initValues(model);
            return "results/createResultsForm";
        }
        Long savedResultsId = resultsService.saveResults(createRequest);
        redirectAttributes.addFlashAttribute("message", Message.CREATED.getMessage());
        return "redirect:/results/" + savedResultsId;
    }

    @PostMapping("{id}/edit")
    public String updateResults(@Valid @ModelAttribute("updateForm") ResultsForm.UpdateRequest updateRequest,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            initValues(model);
            model.addAttribute("results", resultsService.findResults(updateRequest.getId()));
            return "results/updateResultsForm";
        }
        resultsService.updateResults(updateRequest);
        redirectAttributes.addFlashAttribute("message", Message.UPDATED.getMessage());
        return "redirect:/results/" + updateRequest.getId();
    }

    @DeleteMapping("{id}")
    public String deleteResults(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        resultsService.deleteResults(id);
        redirectAttributes.addFlashAttribute("message", Message.DELETED.getMessage());
        return "redirect:/results";
    }

    // == 헬퍼 메소드 == //
    private void initValues(Model model) {
        model.addAttribute("members", memberRepository.findAllByStatus(Status.AVAILABLE));
        model.addAttribute("subjects", subjectRepository.findAllByStatus(Status.AVAILABLE));
        model.addAttribute("testTypes", testTypeRepository.findAllByStatus(Status.AVAILABLE));
    }
}
