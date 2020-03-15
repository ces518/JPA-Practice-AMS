package me.june.academy.domain.member.service;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.BadRequestException;
import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.repository.MemberRepository;
import me.june.academy.domain.member.repository.MemberSearch;
import me.june.academy.domain.member.web.MemberForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-11
 * Time: 17:56
 **/
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 목록 조회
    public Page<Member> findAll(MemberSearch memberSearch, Pageable pageable) {
        return memberRepository.findAll(memberSearch, pageable);
    }

    // 상세 조회
    public Member findMember(Long id) {
        Assert.notNull(id, "Member id should be not null");
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("존재하지 않는 학원생 입니다."));
        return findMember;
    }

    // 등록
    public Long saveMember(MemberForm memberForm) {
        Member member = memberForm.toEntity();
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    // 수정
    @Transactional
    public void updateMember(MemberForm memberForm) {
        Member member = memberForm.toEntity();
        Member findMember = findMember(memberForm.getId());
        if (findMember.isDisabled()) {
            throw new BadRequestException("비활성화 처리된 회원은 수정할 수 없습니다.");
        }
        findMember.update(member);
    }

    // 삭제 (Soft Delete)
    @Transactional
    public void deleteMember(Long id) {
        Member findMember = findMember(id);
        if (findMember.isDisabled()) {
            throw new BadRequestException("이미 비활성화 처리된 회원입니다.");
        }
        findMember.disable();
    }
}
