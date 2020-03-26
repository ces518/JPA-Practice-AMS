package me.june.academy.domain.results.service;

import lombok.RequiredArgsConstructor;
import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.service.MemberService;
import me.june.academy.domain.results.Results;
import me.june.academy.domain.results.repository.ResultsRepository;
import me.june.academy.domain.results.web.ResultsForm;
import me.june.academy.domain.subject.Subject;
import me.june.academy.domain.subject.service.SubjectService;
import me.june.academy.domain.testType.TestType;
import me.june.academy.domain.testType.service.TestTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-26
 * Time: 21:59
 **/
@Service
@RequiredArgsConstructor
public class ResultsService {
    private final ResultsRepository resultsRepository;
    private final MemberService memberService;
    private final SubjectService subjectService;
    private final TestTypeService testTypeService;

    // 목록 조회
    public Page<Results> findAll(Pageable pageable) {
        return resultsRepository.findAll(pageable);
    }

    // 상세 조회
    public Results findResults(Long id) {
        Assert.notNull(id, "Results id should be not null");
        Results results = resultsRepository.findById(id)
                .orElseThrow(() -> new NotFoundResultsException("존재하지 않는 성적입니다."));
        return results;
    }

    // 등록
    @Transactional
    public Long saveResults(ResultsForm resultsForm) {
        Member findMember = memberService.findMember(resultsForm.getMemberId());
        Subject findSubject = subjectService.findSubject(resultsForm.getSubjectId());
        TestType findTestType = testTypeService.findTestType(resultsForm.getTestTypeId());

        Results results = resultsForm.toEntity(findMember, findSubject, findTestType);

        Results savedResults = resultsRepository.save(results);
        return savedResults.getId();
    }

    // 수정
    @Transactional
    public void updateResults(ResultsForm resultsForm) {
        Results findResults = findResults(resultsForm.getId());
        Member findMember = memberService.findMember(resultsForm.getMemberId());
        Subject findSubject = subjectService.findSubject(resultsForm.getSubjectId());
        TestType findTestType = testTypeService.findTestType(resultsForm.getTestTypeId());

        Results results = resultsForm.toEntity(findMember, findSubject, findTestType);

        findResults.update(results);
    }

    // 삭제
    @Transactional
    public void deleteResults(Long id) {
        Results findResults = findResults(id);
        resultsRepository.delete(findResults);
    }
}
