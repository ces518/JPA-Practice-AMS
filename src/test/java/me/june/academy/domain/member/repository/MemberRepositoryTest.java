package me.june.academy.domain.member.repository;

import me.june.academy.domain.member.Member;
import me.june.academy.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @BeforeEach
    public void before() {
        memberRepository.save(createMember("memberA", "경기도 수원", "구운동", "010-1234-1234"));
        memberRepository.save(createMember("memberB", "강원도", "몰라몰라", "010-1234-1234"));
        memberRepository.save(createMember("memberC", "대전", "월평동", "010-1234-1234"));
    }

    @Test
    public void findAll_no_search() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Member> page = memberRepository.findAll(new MemberSearch(), pageRequest);
        List<Member> members = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(members.size()).isEqualTo(3);
        assertThat(members.get(0).getName()).isEqualTo("memberC");
    }

    @Test
    public void findAll_search() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        MemberSearch memberSearch = new MemberSearch("memberB");

        // when
        Page<Member> page = memberRepository.findAll(memberSearch, pageRequest);
        List<Member> members = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(members.size()).isEqualTo(1);
        assertThat(members.get(0).getName()).isEqualTo("memberB");
    }

    private Member createMember(String name, String city, String street, String phone) {
        return Member.builder()
                .name(name)
                .address(new Address(city, street, "1234"))
                .phone(phone)
                .build();
    }

}