package me.june.academy.domain.groupMember.service;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.BadRequestException;
import me.june.academy.domain.groupMember.GroupMember;
import me.june.academy.domain.groupMember.repository.GroupMemberRepository;
import me.june.academy.domain.groups.Groups;
import me.june.academy.domain.groups.repository.GroupsSearch;
import me.june.academy.domain.groups.service.GroupsService;
import me.june.academy.domain.groups.service.NotFoundGroupsException;
import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 20:03
 **/
@Service
@RequiredArgsConstructor
public class GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;
    private final GroupsService groupsService;
    private final MemberService memberService;

    @Transactional
    public Long saveGroupMember(Long groupsId, Long memberId) {
        Groups findGroups = groupsService.findGroups(groupsId);
        Member findMember = memberService.findMember(memberId);

        GroupMember groupMember = new GroupMember(findGroups, findMember);
        GroupMember savedGroupMember = groupMemberRepository.save(groupMember);
        return savedGroupMember.getId();
    }

    @Transactional
    public void deleteGroupMember(Long groupsMemberId) {
        Assert.notNull(groupsMemberId, "GroupMember id should be not null");
        GroupMember findGroupMember = groupMemberRepository.findById(groupsMemberId)
                .orElseThrow(() -> new NotFoundGroupMemberException("존재하지 않는 소속 학원생 입니다."));
        groupMemberRepository.delete(findGroupMember);
    }
}
