package me.june.academy.domain.teacher.service;

import me.june.academy.common.BadRequestException;
import me.june.academy.domain.teacher.Teacher;
import me.june.academy.domain.teacher.repository.TeacherRepository;
import me.june.academy.domain.teacher.repository.TeacherSearch;
import me.june.academy.domain.teacher.web.TeacherForm;
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
class TeacherServiceTest {

    @Autowired TeacherService teacherService;
    @Autowired TeacherRepository teacherRepository;

    @Test
    public void 선생님_목록_조회() throws Exception {
        // given
        teacherRepository.save(new Teacher("teacherA", "010-1234-1234"));
        teacherRepository.save(new Teacher("teacherB", "010-1234-1234"));
        teacherRepository.save(new Teacher("teacherC", "010-1234-1234"));

        TeacherSearch teacherSearch = new TeacherSearch();
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Teacher> page = teacherService.findAll(teacherSearch, pageRequest);
        List<Teacher> teachers = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(teachers.size()).isEqualTo(3);
    }

    @Test
    public void 선생님_목록_조회_teacherC_검색() throws Exception {
        // given
        teacherRepository.save(new Teacher("teacherA", "010-1234-1234"));
        teacherRepository.save(new Teacher("teacherB", "010-1234-1234"));
        teacherRepository.save(new Teacher("teacherC", "010-1234-1234"));

        TeacherSearch teacherSearch = new TeacherSearch("teacherC");
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Teacher> page = teacherService.findAll(teacherSearch, pageRequest);
        List<Teacher> teachers = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(teachers.size()).isEqualTo(1);
        assertThat(teachers.get(0).getName()).isEqualTo("teacherC");
    }

    @Test
    public void 선생님_등록() throws Exception {
        // given
        TeacherForm teacherForm = createTeacherForm("박준영", "010-1234-1234");

        // when
        Long savedTeacherId = teacherService.saveTeacher(teacherForm);
        Optional<Teacher> optionalTeacher = teacherRepository.findById(savedTeacherId);
        Teacher teacher = optionalTeacher.get();

        // then
        assertThat(teacher.getName()).isEqualTo(teacherForm.getName());
        assertThat(teacher.getPhone()).isEqualTo(teacherForm.getPhone());
    }

    @Test
    public void 선생님_상세_조회_성공() throws Exception {
        // given
        Teacher teacher = new Teacher("teachA", "010-1234-1234");
        Teacher savedTeacher = teacherRepository.save(teacher);
        Long savedTeacherId = savedTeacher.getId();

        // when
        Teacher findTeacher = teacherService.findTeacher(savedTeacherId);

        // then
        assertThat(findTeacher.getName()).isEqualTo(teacher.getName());
        assertThat(findTeacher.getPhone()).isEqualTo(teacher.getPhone());
    }

    @Test
    public void 선생님_상세_조회_실패_존재하지_않는_선생님() throws Exception {
        // given
        Teacher teacher = new Teacher("teachA", "010-1234-1234");
        Teacher savedTeacher = teacherRepository.save(teacher);
        Long savedTeacherId = savedTeacher.getId();

        // when
        NotFoundTeacherException exception = Assertions.assertThrows(NotFoundTeacherException.class, () -> {
            Teacher findTeacher = teacherService.findTeacher(savedTeacherId + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 선생님 입니다.");
    }
    
    @Test
    public void 선생님_상세_조회_실패_id값_null() throws Exception {
        // given
        Teacher teacher = new Teacher("teachA", "010-1234-1234");
        Teacher savedTeacher = teacherRepository.save(teacher);
        Long savedTeacherId = savedTeacher.getId();

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Teacher findTeacher = teacherService.findTeacher(null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Teacher id should be not null");
    }

    @Test
    public void 선생님_수정_성공() throws Exception {
        // given
        Teacher teacher = new Teacher("teachA", "010-1234-1234");
        Teacher savedTeacher = teacherRepository.save(teacher);
        Long savedTeacherId = savedTeacher.getId();

        TeacherForm teacherForm = new TeacherForm("teachA수정", "010-5555-6666");
        teacherForm.setId(savedTeacherId);

        // when
        teacherService.updateTeacher(teacherForm);
        Optional<Teacher> optionalTeacher = teacherRepository.findById(savedTeacherId);
        Teacher findTeacher = optionalTeacher.get();

        // then
        assertThat(findTeacher.getName()).isEqualTo(teacherForm.getName());
        assertThat(findTeacher.getPhone()).isEqualTo(teacherForm.getPhone());
    }

    @Test
    public void 선생님_수정_실패_존재하지_않는_선생님() throws Exception {
        // given
        Teacher teacher = new Teacher("teachA", "010-1234-1234");
        Teacher savedTeacher = teacherRepository.save(teacher);
        Long savedTeacherId = savedTeacher.getId();

        TeacherForm teacherForm = new TeacherForm("teachA수정", "010-5555-6666");
        teacherForm.setId(savedTeacherId + 1);

        // when
        NotFoundTeacherException exception = Assertions.assertThrows(NotFoundTeacherException.class, () -> {
            teacherService.updateTeacher(teacherForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 선생님 입니다.");
    }

    @Test
    public void 선생님_수정_실패_id값_null() throws Exception {
        // given
        Teacher teacher = new Teacher("teachA", "010-1234-1234");
        Teacher savedTeacher = teacherRepository.save(teacher);
        Long savedTeacherId = savedTeacher.getId();

        TeacherForm teacherForm = new TeacherForm("teachA수정", "010-5555-6666");
        teacherForm.setId(null);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            teacherService.updateTeacher(teacherForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Teacher id should be not null");
    }

    @Test
    public void 선생님_수정_실패_status_disabled() throws Exception {
        // given
        Teacher teacher = new Teacher("teachA", "010-1234-1234");
        teacher.disable();

        Teacher savedTeacher = teacherRepository.save(teacher);
        Long savedTeacherId = savedTeacher.getId();

        TeacherForm teacherForm = new TeacherForm("teachA수정", "010-5555-6666");
        teacherForm.setId(savedTeacherId);

        // when
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            teacherService.updateTeacher(teacherForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("비활성화 된 선생님의 정보는 수정할 수 없습니다.");
    }

    @Test
    public void 선생님_삭제_성공() throws Exception {
        // given
        Teacher teacher = new Teacher("teachA", "010-1234-1234");
        Teacher savedTeacher = teacherRepository.save(teacher);
        Long savedTeacherId = savedTeacher.getId();

        // when
        teacherService.deleteTeacher(savedTeacherId);
        Optional<Teacher> optionalTeacher = teacherRepository.findById(savedTeacherId);
        Teacher findTeacher = optionalTeacher.get();

        // then
        assertThat(findTeacher.getStatus()).isEqualTo(Status.DISABLED);
    }

    @Test
    public void 선생님_삭제_실패_존재하지_않는_선생님() throws Exception {
        // given
        Teacher teacher = new Teacher("teachA", "010-1234-1234");
        Teacher savedTeacher = teacherRepository.save(teacher);
        Long savedTeacherId = savedTeacher.getId();

        // when
        NotFoundTeacherException exception = Assertions.assertThrows(NotFoundTeacherException.class, () -> {
            teacherService.deleteTeacher(savedTeacherId + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 선생님 입니다.");
    }
    
    @Test
    public void 선생님_삭제_실패_id값_null() throws Exception {
        // given
        Teacher teacher = new Teacher("teachA", "010-1234-1234");
        Teacher savedTeacher = teacherRepository.save(teacher);
        Long savedTeacherId = savedTeacher.getId();

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            teacherService.deleteTeacher(null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Teacher id should be not null");
    }
    
    @Test
    public void 선생님_삭제_실패_status_disabled() throws Exception {
        // given
        Teacher teacher = new Teacher("teachA", "010-1234-1234");
        teacher.disable();

        Teacher savedTeacher = teacherRepository.save(teacher);
        Long savedTeacherId = savedTeacher.getId();

        // when
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            teacherService.deleteTeacher(savedTeacherId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("이미 비활성화 된 선생님 입니다.");
    }

    private TeacherForm createTeacherForm(String name, String phone) {
        return new TeacherForm(name, phone);
    }
}