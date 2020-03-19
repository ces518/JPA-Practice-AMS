package me.june.academy.domain.grade.service;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.BadRequestException;
import me.june.academy.domain.grade.Grade;
import me.june.academy.domain.grade.repository.GradeRepository;
import me.june.academy.domain.grade.repository.GradeSearch;
import me.june.academy.domain.grade.web.GradeForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-19
 * Time: 17:02
 **/
@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;

    // 목록 조회
    public Page<Grade> findAll(GradeSearch gradeSearch, Pageable pageable) {
        return gradeRepository.findAll(gradeSearch, pageable);
    }

    // 상세 조회
    public Grade findGrade(Long id) {
        Assert.notNull(id, "Grade id should be not null");
        Grade findGrade = gradeRepository.findById(id)
                .orElseThrow(() -> new NotFoundGradeException("존재하지 않는 반 입니다."));
        return findGrade;
    }

    // 등록
    public Long saveGrade(GradeForm gradeForm) {
        Grade grade = gradeForm.toEntity();
        Grade savedGrade = gradeRepository.save(grade);
        return savedGrade.getId();
    }

    // 수정
    @Transactional
    public void updateGrade(GradeForm gradeForm) {
        Grade grade = gradeForm.toEntity();
        Grade findGrade = findGrade(gradeForm.getId());
        if (findGrade.isDisabled()) {
            throw new BadRequestException("비활성화 처리된 반의 정보는 수정할 수 없습니다.");
        }
        findGrade.update(grade);
    }

    // 삭제
    @Transactional
    public void deleteGrade(Long id) {
        Grade findGrade = findGrade(id);
        if (findGrade.isDisabled()) {
            throw new BadRequestException("이미 비활성화 처리된 반 입니다.");
        }
        findGrade.disable();
    }
}
