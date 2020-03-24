package me.june.academy.domain.subject.service;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.BadRequestException;
import me.june.academy.domain.subject.Subject;
import me.june.academy.domain.subject.repository.SubjectRepository;
import me.june.academy.domain.subject.repository.SubjectSearch;
import me.june.academy.domain.subject.web.SubjectForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 11:02
 **/
@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    // 목록 조회
    public Page<Subject> findAll(SubjectSearch subjectSearch, Pageable pageable) {
        return subjectRepository.findAll(subjectSearch, pageable);
    }

    // 상세 조회
    public Subject findSubject(Long id) {
        Assert.notNull(id, "Subject id should be not null");
        Subject findSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundSubjectException("존재하지 않는 과목 입니다."));
        return findSubject;
    }

    // 등록
    public Long saveSubject(SubjectForm subjectForm) {
        Subject subject = subjectForm.toEntity();
        Subject savedSubject = subjectRepository.save(subject);
        return savedSubject.getId();
    }

    // 수정
    @Transactional
    public void updateSubject(SubjectForm subjectForm) {
        Subject subject = subjectForm.toEntity();
        Subject findSubject = findSubject(subjectForm.getId());
        if (findSubject.isDisabled()) {
            throw new BadRequestException("비활성화 상태인 과목의 정보는 수정할 수 없습니다.");
        }
        findSubject.update(subject);
    }

    // 삭제
    @Transactional
    public void deleteSubject(Long id) {
        Subject findSubject = findSubject(id);
        if (findSubject.isDisabled()) {
            throw new BadRequestException("이미 비활성화된 과목입니다.");
        }
        findSubject.disable();
    }
}
