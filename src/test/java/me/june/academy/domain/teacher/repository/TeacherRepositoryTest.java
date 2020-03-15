package me.june.academy.domain.teacher.repository;

import me.june.academy.domain.teacher.Teacher;
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
class TeacherRepositoryTest {

    @Autowired TeacherRepository teacherRepository;

    @BeforeEach
    public void before() {
        teacherRepository.save(new Teacher("teacherA", "010-1234-1234"));
        teacherRepository.save(new Teacher("teacherB", "010-1234-1234"));
        teacherRepository.save(new Teacher("teacherC", "010-1234-1234"));
    }

    @Test
    public void findAll_no_search() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        TeacherSearch teacherSearch = new TeacherSearch();

        // when
        Page<Teacher> page = teacherRepository.findAll(teacherSearch, pageRequest);
        List<Teacher> teachers = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(teachers.size()).isEqualTo(3);
    }
    
    @Test
    public void findAll_search() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        TeacherSearch teacherSearch = new TeacherSearch("teacherB");

        // when
        Page<Teacher> page = teacherRepository.findAll(teacherSearch, pageRequest);
        List<Teacher> teachers = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(teachers.size()).isEqualTo(1);
        assertThat(teachers.get(0).getName()).isEqualTo("teacherB");
    }
}