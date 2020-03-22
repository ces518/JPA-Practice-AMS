package me.june.academy.domain.grade.repository;

import me.june.academy.domain.grade.Grade;
import me.june.academy.model.Status;
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
class GradeRepositoryTest {

    @Autowired GradeRepository gradeRepository;

    @BeforeEach
    public void before() {
        gradeRepository.save(new Grade("gradeA"));
        gradeRepository.save(new Grade("gradeB"));
        gradeRepository.save(new Grade("gradeC"));
    }

    @Test
    public void findAllByStatus() throws Exception {
        // when
        List<Grade> findGrades = gradeRepository.findAllByStatus(Status.AVAILABLE);

        // then
        assertThat(findGrades)
                .extracting("status")
                .contains(Status.AVAILABLE);
    }

    @Test
    public void findAll_no_search() throws Exception {
        // given
        GradeSearch gradeSearch = new GradeSearch();
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Grade> page = gradeRepository.findAll(gradeSearch, pageRequest);
        List<Grade> grades = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(grades.size()).isEqualTo(3);
    }

    @Test
    public void findAll_search_gradeA() throws Exception {
        // given
        GradeSearch gradeSearch = new GradeSearch("gradeA");
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Grade> page = gradeRepository.findAll(gradeSearch, pageRequest);
        List<Grade> grades = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(grades.size()).isEqualTo(1);
        assertThat(grades)
                .extracting("name")
                .containsExactly("gradeA");
    }
}