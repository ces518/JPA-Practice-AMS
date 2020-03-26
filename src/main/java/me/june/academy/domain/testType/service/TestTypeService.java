package me.june.academy.domain.testType.service;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.BadRequestException;
import me.june.academy.domain.testType.TestType;
import me.june.academy.domain.testType.repository.TestTypeRepository;
import me.june.academy.domain.testType.repository.TestTypeSearch;
import me.june.academy.domain.testType.web.TestTypeForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 22:57
 **/
@Service
@RequiredArgsConstructor
public class TestTypeService {
    private final TestTypeRepository testTypeRepository;

    // 목록 조회
    public Page<TestType> findAll(TestTypeSearch testTypeSearch, Pageable pageable) {
        return testTypeRepository.findAll(testTypeSearch, pageable);
    }

    // 상세 조회
    public TestType findTestType(Long id) {
        Assert.notNull(id, "TestType id should be not null");
        return testTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundTestTypeException("존재하지 않는 시험타입 입니다."));
    }

    // 등록
    public Long saveTestType(TestTypeForm testTypeForm) {
        TestType testType = testTypeForm.toEntity();
        TestType savedTestType = testTypeRepository.save(testType);
        return savedTestType.getId();
    }

    // 수정
    @Transactional
    public void updateTestType(TestTypeForm testTypeForm) {
        TestType testType = testTypeForm.toEntity();
        TestType findTestType = findTestType(testTypeForm.getId());
        if (findTestType.isDisabled()) {
            throw new BadRequestException("비활성화 상태인 시험 타입의 정보는 수정할 수 없습니다.");
        }
        findTestType.update(testType);
    }

    // 삭제
    @Transactional
    public void deleteTestType(Long id) {
        TestType findTestType = findTestType(id);
        if (findTestType.isDisabled()) {
            throw new BadRequestException("이미 비활성화 된 시험 타입 입니다.");
        }
        findTestType.disabled();
    }
}
