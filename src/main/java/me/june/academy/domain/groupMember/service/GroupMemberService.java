package me.june.academy.domain.groupMember.service;

import lombok.RequiredArgsConstructor;
import me.june.academy.domain.groupMember.GroupMember;
import me.june.academy.domain.groupMember.repository.GroupMemberRepository;
import me.june.academy.domain.groups.Groups;
import me.june.academy.domain.groups.service.GroupsService;
import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

    public Optional<GroupMember> findGroupMember(Long groupsId, Long memberId) {
        Assert.notNull(groupsId, "Groups id should be not null");
        Assert.notNull(memberId, "Member id should be not null");
        return groupMemberRepository.findByGroupIdAndMemberId(groupsId, memberId);
    }

    @Transactional
    public Long saveGroupMember(Long groupsId, Long memberId) {
        Groups findGroups = groupsService.findGroups(groupsId);
        Member findMember = memberService.findMember(memberId);

        // 중복 검사
        findGroupMember(groupsId, memberId)
                .ifPresent(groupMember -> {
                    throw new DuplicateGroupMemberException("이미 소속된 학원생 입니다.");
                });

        GroupMember groupMember = new GroupMember(findGroups, findMember);
        GroupMember savedGroupMember = groupMemberRepository.save(groupMember);
        return savedGroupMember.getId();
    }

    @Transactional
    public void deleteGroupMember(Long groupsId, Long memberId) {
        GroupMember findGroupMember = findGroupMember(groupsId, memberId)
                .orElseThrow(() -> new NotFoundGroupMemberException("존재하지 않는 소속 학원생 입니다."));

        groupMemberRepository.delete(findGroupMember);
    }
}
