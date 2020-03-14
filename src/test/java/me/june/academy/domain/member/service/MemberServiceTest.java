package me.june.academy.domain.member.service;

import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.repository.MemberRepository;
import me.june.academy.domain.member.repository.MemberSearch;
import me.june.academy.domain.member.web.MemberForm;
import me.june.academy.model.Address;
import me.june.academy.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 학원생_조회() throws Exception {
        // given
        memberRepository.save(createMember("memberA", "경기도 수원", "구운동", "010-1234-1234"));
        memberRepository.save(createMember("memberB", "강원도", "몰라몰라", "010-1234-1234"));
        memberRepository.save(createMember("memberC", "대전", "월평동", "010-1234-1234"));

        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Member> page = memberService.findAll(new MemberSearch(), pageRequest);
        List<Member> members = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(members.size()).isEqualTo(3);
        assertThat(members.get(0).getName()).isEqualTo("memberC");
    }

    @Test
    public void 학원생_조회_memberB검색() throws Exception {
        // given
        memberRepository.save(createMember("memberA", "경기도 수원", "구운동", "010-1234-1234"));
        memberRepository.save(createMember("memberB", "강원도", "몰라몰라", "010-1234-1234"));
        memberRepository.save(createMember("memberC", "대전", "월평동", "010-1234-1234"));

        PageRequest pageRequest = PageRequest.of(0, 10);
        MemberSearch memberSearch = new MemberSearch("memberB");

        // when
        Page<Member> page = memberService.findAll(memberSearch, pageRequest);
        List<Member> members = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(members.size()).isEqualTo(1);
        assertThat(members.get(0).getName()).isEqualTo("memberB");
    }

    @Test
    public void 학원생_등록() throws Exception {
        // given
        MemberForm memberForm = createMemberForm("memberA", "경기도 수원시", "구운동");

        // when
        Long savedMemberId = memberService.saveMember(memberForm);
        Optional<Member> optionalMember = memberRepository.findById(savedMemberId);
        Member findMember = optionalMember.get();

        // then
        assertThat(findMember.getName()).isEqualTo(memberForm.getName());
        assertThat(findMember.getPhone()).isEqualTo(memberForm.getPhone());
        assertThat(findMember.getAddress().getCity()).isEqualTo(memberForm.getCity());
        assertThat(findMember.getAddress().getStreet()).isEqualTo(memberForm.getStreet());
        assertThat(findMember.getAddress().getZipcode()).isEqualTo(memberForm.getZipcode());
        assertThat(findMember.getStatus()).isEqualTo(Status.AVAILABLE);
    }
    
    @Test
    public void 학원생_상세조회_성공() throws Exception {
        // given
        MemberForm memberForm = createMemberForm("memberA", "경기도 수원시", "구운동");
        Long savedMemberId = memberService.saveMember(memberForm);

        // when
        Member findMember = memberService.findMember(savedMemberId);

        // then
        assertThat(findMember.getName()).isEqualTo(memberForm.getName());
        assertThat(findMember.getPhone()).isEqualTo(memberForm.getPhone());
        assertThat(findMember.getAddress().getCity()).isEqualTo(memberForm.getCity());
        assertThat(findMember.getAddress().getStreet()).isEqualTo(memberForm.getStreet());
        assertThat(findMember.getAddress().getZipcode()).isEqualTo(memberForm.getZipcode());
        assertThat(findMember.getStatus()).isEqualTo(Status.AVAILABLE);
    }

    @Test
    public void 학원생_상세조회_존재하지_않는_회원() throws Exception {
        // given
        MemberForm memberForm = createMemberForm("memberA", "경기도 수원시", "구운동");
        Long savedMemberId = memberService.saveMember(memberForm);

        // when
        NotFoundMemberException exception = Assertions.assertThrows(NotFoundMemberException.class, () -> {
            Member findMember = memberService.findMember(savedMemberId + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 학원생 입니다.");
    }

    @Test
    public void 학원생_상세조회_id값_null() throws Exception {
        // given
        MemberForm memberForm = createMemberForm("memberA", "경기도 수원시", "구운동");
        Long savedMemberId = memberService.saveMember(memberForm);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Member findMember = memberService.findMember(null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Member id should be not null");
    }

    @Test
    public void 학원생_수정_성공() throws Exception {
        // given
        Long savedMemberId = memberService.saveMember(createMemberForm("memberA", "경기도 수원시", "구운동"));
        MemberForm memberForm = createMemberForm("memberA수정", "강원도", " 몰라몰라");
        memberForm.setId(savedMemberId);

        // when
        memberService.updateMember(memberForm);
        Member findMember = memberService.findMember(memberForm.getId());

        // then
        assertThat(findMember.getName()).isEqualTo(memberForm.getName());
        assertThat(findMember.getPhone()).isEqualTo(memberForm.getPhone());
        assertThat(findMember.getAddress().getCity()).isEqualTo(memberForm.getCity());
        assertThat(findMember.getAddress().getStreet()).isEqualTo(memberForm.getStreet());
        assertThat(findMember.getAddress().getZipcode()).isEqualTo(memberForm.getZipcode());
        assertThat(findMember.getStatus()).isEqualTo(Status.AVAILABLE);
    }

    @Test
    public void 학원생_수정_존재하지_않는_학원생() throws Exception {
        // given
        Long savedMemberId = memberService.saveMember(createMemberForm("memberA", "경기도 수원시", "구운동"));
        MemberForm memberForm = createMemberForm("memberA수정", "강원도", " 몰라몰라");
        memberForm.setId(savedMemberId + 1);

        // when
        NotFoundMemberException exception = Assertions.assertThrows(NotFoundMemberException.class, () -> {
            memberService.updateMember(memberForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 학원생 입니다.");
    }

    @Test
    public void 학원생_수정_id값_null() throws Exception {
        // given
        Long savedMemberId = memberService.saveMember(createMemberForm("memberA", "경기도 수원시", "구운동"));
        MemberForm memberForm = createMemberForm("memberA수정", "강원도", " 몰라몰라");
        memberForm.setId(null);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            memberService.updateMember(memberForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Member id should be not null");
    }

    @Test
    public void 학원생_삭제() throws Exception {
        // given
        MemberForm memberForm = createMemberForm("memberA", "경기도 수원시", "구운동");
        Long savedMemberId = memberService.saveMember(memberForm);

        // when
        memberService.deleteMember(savedMemberId);
        Member findMember = memberService.findMember(savedMemberId);

        // then
        assertThat(findMember.getStatus()).isEqualTo(Status.DISABLED);
    }

    @Test
    public void 학원생_삭제_존재하지_않는_학원생() throws Exception {
        // given
        MemberForm memberForm = createMemberForm("memberA", "경기도 수원시", "구운동");
        Long savedMemberId = memberService.saveMember(memberForm);

        // when
        NotFoundMemberException exception = Assertions.assertThrows(NotFoundMemberException.class, () -> {
            memberService.deleteMember(savedMemberId + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 학원생 입니다.");
    }

    @Test
    public void 학원생_삭제_id값_null() throws Exception {
        // given
        MemberForm memberForm = createMemberForm("memberA", "경기도 수원시", "구운동");
        Long savedMemberId = memberService.saveMember(memberForm);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            memberService.deleteMember(null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Member id should be not null");
    }

    private Member createMember(String name, String city, String street, String phone) {
        return Member.builder()
                .name(name)
                .address(new Address(city, street, "1234"))
                .phone(phone)
                .build();
    }

    private MemberForm createMemberForm(String name, String city, String street) {
        return MemberForm.builder()
                .name(name)
                .city(city)
                .street(street)
                .zipcode("1234")
                .phone("010-1234-1234")
                .build();
    }

}