package me.june.academy.domain.groupMember.service;

import me.june.academy.domain.groupMember.GroupMember;
import me.june.academy.domain.groupMember.repository.GroupMemberRepository;
import me.june.academy.domain.groups.Groups;
import me.june.academy.domain.groups.repository.GroupsRepository;
import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.repository.MemberRepository;
import me.june.academy.model.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class GroupMemberServiceTest {

    @Autowired GroupsRepository groupsRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired GroupMemberService groupMemberService;
    @Autowired GroupMemberRepository groupMemberRepository;

    @Test
    public void 반_소속학생_등록() throws Exception {
        // given
        Groups groupsA = groupsRepository.save(new Groups("groupsA"));
        Member memberA = memberRepository.save(createMember("memberA", "경기도", "수원시", "010-1234-1234"));

        // when
        Long savedGroupMemberId = groupMemberService.saveGroupMember(groupsA.getId(), memberA.getId());
        Optional<GroupMember> optionalGroupMember = groupMemberRepository.findById(savedGroupMemberId);
        GroupMember findGroupMember = optionalGroupMember.get();

        // then
        assertThat(findGroupMember.getGroup()).isEqualTo(groupsA);
        assertThat(findGroupMember.getGroup().getName()).isEqualTo(groupsA.getName());
        assertThat(findGroupMember.getMember()).isEqualTo(memberA);
        assertThat(findGroupMember.getMember().getName()).isEqualTo(memberA.getName());

    }

    @Test
    public void 반_소속학생_등록_실패_중복등록() throws Exception {
        // given
        Groups groupsA = groupsRepository.save(new Groups("groupsA"));
        Member memberA = memberRepository.save(createMember("memberA", "경기도", "수원시", "010-1234-1234"));

        // when
        groupMemberService.saveGroupMember(groupsA.getId(), memberA.getId());
        DuplicateGroupMemberException exception = Assertions.assertThrows(DuplicateGroupMemberException.class, () -> {
            groupMemberService.saveGroupMember(groupsA.getId(), memberA.getId());
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("이미 소속된 학원생 입니다.");
    }

    @Test
    public void 반_소속학생_삭제_성공() throws Exception {
        // given
        Groups groupsA = groupsRepository.save(new Groups("groupsA"));
        Member memberA = memberRepository.save(createMember("memberA", "경기도", "수원시", "010-1234-1234"));

        GroupMember groupMember = new GroupMember(groupsA, memberA);
        GroupMember savedGroupMember = groupMemberRepository.save(groupMember);

        // when
        groupMemberService.deleteGroupMember(groupsA.getId(), memberA.getId());
        Optional<GroupMember> optionalGroupMember = groupMemberRepository.findById(savedGroupMember.getId());

        // then
        assertThat(optionalGroupMember.isPresent()).isFalse();
    }
    
    @Test
    public void 반_소속학생_삭제_실패_존재하지_않는_소속학원생() throws Exception {
        // given
        Groups groupsA = groupsRepository.save(new Groups("groupsA"));
        Member memberA = memberRepository.save(createMember("memberA", "경기도", "수원시", "010-1234-1234"));

        GroupMember groupMember = new GroupMember(groupsA, memberA);
        GroupMember savedGroupMember = groupMemberRepository.save(groupMember);

        // when
        NotFoundGroupMemberException exception = Assertions.assertThrows(NotFoundGroupMemberException.class, () -> {
            groupMemberService.deleteGroupMember(groupsA.getId() + 1, memberA.getId() + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 소속 학원생 입니다.");
    }

    @Test
    public void 반_소속학생_삭제_실패_groupsId값_null() throws Exception {
        // given
        Groups groupsA = groupsRepository.save(new Groups("groupsA"));
        Member memberA = memberRepository.save(createMember("memberA", "경기도", "수원시", "010-1234-1234"));

        GroupMember groupMember = new GroupMember(groupsA, memberA);
        GroupMember savedGroupMember = groupMemberRepository.save(groupMember);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            groupMemberService.deleteGroupMember(null, memberA.getId());
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Groups id should be not null");
    }
    
    @Test
    public void 반_소속학생_삭제_실패_memberId값_null() throws Exception {
        // given
        Groups groupsA = groupsRepository.save(new Groups("groupsA"));
        Member memberA = memberRepository.save(createMember("memberA", "경기도", "수원시", "010-1234-1234"));

        GroupMember groupMember = new GroupMember(groupsA, memberA);
        GroupMember savedGroupMember = groupMemberRepository.save(groupMember);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            groupMemberService.deleteGroupMember(groupsA.getId(), null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Member id should be not null");
    }
    
    @Test
    public void 반_소속학생_삭제_실패_groupsId_memberId값_null() throws Exception {
        // given
        Groups groupsA = groupsRepository.save(new Groups("groupsA"));
        Member memberA = memberRepository.save(createMember("memberA", "경기도", "수원시", "010-1234-1234"));

        GroupMember groupMember = new GroupMember(groupsA, memberA);
        GroupMember savedGroupMember = groupMemberRepository.save(groupMember);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            groupMemberService.deleteGroupMember(null, null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Groups id should be not null");
    }
    
    private Member createMember(String name, String city, String street, String phone) {
        return Member.builder()
                .name(name)
                .address(new Address(city, street, "1234"))
                .phone(phone)
                .build();
    }
}