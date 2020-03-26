package me.june.academy.domain.results.web;

import lombok.RequiredArgsConstructor;
import me.june.academy.domain.results.Results;
import me.june.academy.domain.results.repository.ResultsSearch;
import me.june.academy.domain.results.service.ResultsService;
import me.june.academy.utils.PageWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private final ResultsService resultsService;

    @GetMapping
    public String list(ResultsSearch resultsSearch,
                       Pageable pageable,
                       Model model) {
        Page<Results> resultsPage = resultsService.findAll(resultsSearch, pageable);
        PageWrapper<Results> page = new PageWrapper<>(resultsPage, "/results");

        model.addAttribute("resultsPage", resultsPage);
        model.addAttribute("page", page);
        return "results/list";
    }

    @GetMapping("new")
    public String form(Model model) {
        model.addAttribute("resultsForm", new ResultsForm.CreateRequest());
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
        model.addAttribute("results", findResults);
        model.addAttribute("resultsForm", new ResultsForm.UpdateRequest(findResults));
        return "results/updateResultsForm";
    }
}
