package me.june.academy.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import me.june.academy.common.BadRequestException;
import me.june.academy.domain.teacher.Teacher;
import me.june.academy.domain.teacher.repository.TeacherRepository;
import me.june.academy.domain.teacher.repository.TeacherSearch;
import me.june.academy.domain.teacher.web.TeacherForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-15
 * Time: 22:59
 **/
@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    // 목록 조회
    public Page<Teacher> findAll(TeacherSearch teacherSearch, Pageable pageable) {
        return teacherRepository.findAll(teacherSearch, pageable);
    }

    // 상세 조회
    public Teacher findTeacher(Long id) {
        Assert.notNull(id, "Teacher id should be not null");
        Teacher findTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundTeacherException("존재하지 않는 선생님 입니다."));
        return findTeacher;
    }

    // 등록
    public Long saveTeacher(TeacherForm teacherForm) {
        Teacher teacher = teacherForm.toEntity();
        Teacher savedTeacher = teacherRepository.save(teacher);
        return savedTeacher.getId();
    }

    // 수정
    @Transactional
    public void updateTeacher(TeacherForm teacherForm) {
        Teacher teacher = teacherForm.toEntity();
        Teacher findTeacher = findTeacher(teacherForm.getId());
        if (findTeacher.isDisabled()) {
            throw new BadRequestException("비활성화 된 선생님의 정보는 수정할 수 없습니다.");
        }
        findTeacher.update(teacher);
    }

    // 삭제(Soft Delete)
    @Transactional
    public void deleteTeacher(Long id) {
        Teacher findTeacher = findTeacher(id);
        if (findTeacher.isDisabled()) {
            throw new BadRequestException("이미 비활성화 된 선생님 입니다.");
        }
        findTeacher.disable();
    }
}
