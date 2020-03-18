package me.june.academy.domain.groups.web;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.Message;
import me.june.academy.domain.groups.Groups;
import me.june.academy.domain.groups.repository.GroupsSearch;
import me.june.academy.domain.groups.service.GroupsService;
import me.june.academy.domain.groups.validator.GroupsValidator;
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
 * Date: 2020-03-18
 * Time: 19:25
 **/
@Controller
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupsController {
    private static final String GROUPS_FORM = "groups/regist";
    private final GroupsService groupsService;

    @InitBinder("groupsForm")
    public void groupsValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new GroupsValidator());
    }

    @GetMapping
    public String list(GroupsSearch groupsSearch,
                        Pageable pageable,
                        Model model) {
        Page<Groups> groupsPage = groupsService.findAll(groupsSearch, pageable);
        PageWrapper<Groups> page = new PageWrapper<>(groupsPage, "/groups");

        model.addAttribute("groupsPage", groupsPage);
        model.addAttribute("page", page);
        return "groups/list";
    }

    @GetMapping("new")
    public String form(Model model) {
        model.addAttribute("groupsForm", new GroupsForm());
        return GROUPS_FORM;
    }

    @GetMapping("{id}")
    public String view(@PathVariable Long id, Model model) {
        Groups findGroups = groupsService.findGroups(id);
        model.addAttribute("groups", findGroups);
        return "groups/view";
    }

    @GetMapping("{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Groups findGroups = groupsService.findGroups(id);
        model.addAttribute("groupsForm", new GroupsForm(findGroups));
        return GROUPS_FORM;
    }

    @PostMapping("new")
    public String createGroups(@Valid @ModelAttribute GroupsForm groupsForm,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return GROUPS_FORM;
        }
        Long savedGroupsId = groupsService.saveGroups(groupsForm);
        redirectAttributes.addFlashAttribute("message", Message.CREATED.getMessage());
        return "redirect:/groups/" + savedGroupsId;
    }

    @PostMapping("{id}/edit")
    public String updateGroups(@Valid @ModelAttribute GroupsForm groupsForm,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return GROUPS_FORM;
        }
        groupsService.updateGroups(groupsForm);
        redirectAttributes.addFlashAttribute("message", Message.UPDATED.getMessage());
        return "redirect:/groups/" + groupsForm.getId();
    }

    @DeleteMapping("{id}")
    public String deleteGroups(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        groupsService.deleteGroups(id);
        redirectAttributes.addFlashAttribute("message", Message.DELETED.getMessage());
        return "redirect:/groups";
    }

}
