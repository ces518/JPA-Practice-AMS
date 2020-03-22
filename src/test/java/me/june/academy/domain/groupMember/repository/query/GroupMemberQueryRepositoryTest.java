package me.june.academy.domain.groupMember.repository.query;

import me.june.academy.domain.grade.Grade;
import me.june.academy.domain.grade.repository.GradeRepository;
import me.june.academy.domain.groupMember.GroupMember;
import me.june.academy.domain.groupMember.repository.GroupMemberRepository;
import me.june.academy.domain.groups.Groups;
import me.june.academy.domain.groups.repository.GroupsRepository;
import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.repository.MemberRepository;
import me.june.academy.model.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class GroupMemberQueryRepositoryTest {

    @Autowired GroupMemberRepository groupMemberRepository;
    @Autowired GroupMemberQueryRepository groupMemberQueryRepository;
    @Autowired GroupsRepository groupsRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired GradeRepository gradeRepository;

    @Test
    public void findAllByGroupsId() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups groupsA = groupsRepository.save(new Groups("groupsA", savedGrade));
        Groups groupsB = groupsRepository.save(new Groups("groupsB", savedGrade));

        Member memberA = memberRepository.save(createMember("memberA", "경기도", "수원시", "010-1234-1234"));
        Member memberB = memberRepository.save(createMember("memberB", "대전", "월평동", "010-1234-1234"));
        Member memberC = memberRepository.save(createMember("memberC", "강원도", "몰라몰라", "010-1234-1234"));

        groupMemberRepository.save(new GroupMember(groupsA, memberA));
        groupMemberRepository.save(new GroupMember(groupsA, memberB));
        groupMemberRepository.save(new GroupMember(groupsB, memberC));

        // when
        List<GroupMemberQueryDto> groupMembers = groupMemberQueryRepository.findAllByGroupsId(groupsA.getId());

        // then
        assertThat(groupMembers)
                .extracting("memberName")
                .containsExactly(memberA.getName(), memberB.getName());
    }

    private Member createMember(String name, String city, String street, String phone) {
        return Member.builder()
                .name(name)
                .address(new Address(city, street, "1234"))
                .phone(phone)
                .build();
    }
}