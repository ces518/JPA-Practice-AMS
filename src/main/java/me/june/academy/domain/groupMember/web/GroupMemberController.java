package me.june.academy.domain.groupMember.web;

import lombok.RequiredArgsConstructor;
import me.june.academy.domain.groupMember.service.GroupMemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 21:59
 **/
@Controller
@RequestMapping("/groups/{groupsId}/members")
@RequiredArgsConstructor
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    @PostMapping
    public String createGroupMember(@PathVariable Long groupsId, @RequestParam Long memberId) {
        groupMemberService.saveGroupMember(groupsId, memberId);
        return "redirect:/groups/{groupsId}";
    }

    @DeleteMapping("{memberId}")
    public String deleteGroupMember(@PathVariable Long groupsId, @PathVariable Long memberId) {
        groupMemberService.deleteGroupMember(groupsId, memberId);
        return "redirect:/groups/{groupsId}";
    }
}
