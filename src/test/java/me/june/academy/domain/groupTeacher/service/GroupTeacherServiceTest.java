package me.june.academy.domain.groupTeacher.service;

import me.june.academy.domain.grade.Grade;
import me.june.academy.domain.grade.repository.GradeRepository;
import me.june.academy.domain.groupTeacher.GroupTeacher;
import me.june.academy.domain.groupTeacher.repository.GroupTeacherRepository;
import me.june.academy.domain.groups.Groups;
import me.june.academy.domain.groups.repository.GroupsRepository;
import me.june.academy.domain.teacher.Teacher;
import me.june.academy.domain.teacher.repository.TeacherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class GroupTeacherServiceTest {

    @Autowired GroupTeacherService groupTeacherService;
    @Autowired GroupTeacherRepository groupTeacherRepository;
    @Autowired GradeRepository gradeRepository;
    @Autowired GroupsRepository groupsRepository;
    @Autowired TeacherRepository teacherRepository;

    @Test
    public void 담임선생님_등록_성공() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroup = groupsRepository.save(new Groups("groupsA", savedGrade));
        Teacher savedTeacher = teacherRepository.save(new Teacher("teacherA", "010-1234-1234"));

        // when
        Long savedGroupTeacherId = groupTeacherService.saveGroupTeacher(savedGroup.getId(), savedTeacher.getId());
        Optional<GroupTeacher> optionalGroupTeacher = groupTeacherRepository.findById(savedGroupTeacherId);
        GroupTeacher findGroupTeacher = optionalGroupTeacher.get();

        // then
        assertThat(findGroupTeacher.getGroup()).isEqualTo(savedGroup);
        assertThat(findGroupTeacher.getTeacher()).isEqualTo(savedTeacher);
    }

    @Test
    public void 담임선생님_등록_실패_중복등록() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroup = groupsRepository.save(new Groups("groupsA", savedGrade));
        Teacher savedTeacher = teacherRepository.save(new Teacher("teacherA", "010-1234-1234"));
        groupTeacherService.saveGroupTeacher(savedGroup.getId(), savedTeacher.getId());

        // when
        DuplicateGroupTeacherException exception = Assertions.assertThrows(DuplicateGroupTeacherException.class, () -> {
            groupTeacherService.saveGroupTeacher(savedGroup.getId(), savedTeacher.getId());
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("이미 지정된 담임 선생님 입니다.");
    }

    @Test
    public void 담임선생님_삭제_성공() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroup = groupsRepository.save(new Groups("groupsA", savedGrade));
        Teacher savedTeacher = teacherRepository.save(new Teacher("teacherA", "010-1234-1234"));
        Long savedGroupTeacherId = groupTeacherService.saveGroupTeacher(savedGroup.getId(), savedTeacher.getId());

        // when
        groupTeacherService.deleteGroupTeacher(savedGroup.getId(), savedTeacher.getId());
        Optional<GroupTeacher> optionalGroupTeacher = groupTeacherRepository.findById(savedGroupTeacherId);

        // then
        assertThat(optionalGroupTeacher.isPresent()).isFalse();
    }

    @Test
    public void 담임선생님_삭제_실패_존재하지_않는_담임선생님() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroup = groupsRepository.save(new Groups("groupsA", savedGrade));
        Teacher savedTeacher = teacherRepository.save(new Teacher("teacherA", "010-1234-1234"));
        Long savedGroupTeacherId = groupTeacherService.saveGroupTeacher(savedGroup.getId(), savedTeacher.getId());

        // when
        NotFoundGroupTeacherException exception = Assertions.assertThrows(NotFoundGroupTeacherException.class, () -> {
            groupTeacherService.deleteGroupTeacher(savedGroup.getId() + 1, savedTeacher.getId() + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 담임 선생님 입니다.");
    }

    @Test
    public void 담임선생님_삭제_실패_groupsId값_null() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroup = groupsRepository.save(new Groups("groupsA", savedGrade));
        Teacher savedTeacher = teacherRepository.save(new Teacher("teacherA", "010-1234-1234"));
        Long savedGroupTeacherId = groupTeacherService.saveGroupTeacher(savedGroup.getId(), savedTeacher.getId());

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            groupTeacherService.deleteGroupTeacher(null, savedTeacher.getId());
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Groups id should be not null");
    }

    @Test
    public void 담임선생님_삭제_실패_teacherId값_null() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroup = groupsRepository.save(new Groups("groupsA", savedGrade));
        Teacher savedTeacher = teacherRepository.save(new Teacher("teacherA", "010-1234-1234"));
        Long savedGroupTeacherId = groupTeacherService.saveGroupTeacher(savedGroup.getId(), savedTeacher.getId());

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            groupTeacherService.deleteGroupTeacher(savedGroup.getId(), null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Teacher id should be not null");
    }
}