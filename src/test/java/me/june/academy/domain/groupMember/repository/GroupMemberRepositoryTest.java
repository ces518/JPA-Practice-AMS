package me.june.academy.domain.groupMember.repository;

import me.june.academy.domain.grade.Grade;
import me.june.academy.domain.grade.repository.GradeRepository;
import me.june.academy.domain.groupMember.GroupMember;
import me.june.academy.domain.groupMember.repository.query.GroupMemberQueryDto;
import me.june.academy.domain.groupMember.repository.query.GroupMemberQueryRepository;
import me.june.academy.domain.groups.Groups;
import me.june.academy.domain.groups.repository.GroupsRepository;
import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.repository.MemberRepository;
import me.june.academy.model.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class GroupMemberRepositoryTest {

    @Autowired GroupMemberRepository groupMemberRepository;
    @Autowired GroupsRepository groupsRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired GradeRepository gradeRepository;

    @Test
    public void findByGroupsIdAndMember() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups groupsA = groupsRepository.save(new Groups("groupsA", savedGrade));
        Member memberA = memberRepository.save(createMember("memberA", "경기도", "수원시", "010-1234-1234"));

        GroupMember savedGroupMember = groupMemberRepository.save(new GroupMember(groupsA, memberA));

        // when
        Optional<GroupMember> optionalGroupMember = groupMemberRepository.findByGroupIdAndMemberId(groupsA.getId(), memberA.getId());
        GroupMember findGroupMember = optionalGroupMember.get();

        // then
        assertThat(findGroupMember).isEqualTo(savedGroupMember);
        assertThat(findGroupMember.getGroup()).isEqualTo(groupsA);
        assertThat(findGroupMember.getMember()).isEqualTo(memberA);
    }

    private Member createMember(String name, String city, String street, String phone) {
        return Member.builder()
                .name(name)
                .address(new Address(city, street, "1234"))
                .phone(phone)
                .build();
    }
}