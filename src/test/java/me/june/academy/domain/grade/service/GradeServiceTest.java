package me.june.academy.domain.grade.service;

import me.june.academy.common.BadRequestException;
import me.june.academy.domain.grade.Grade;
import me.june.academy.domain.grade.repository.GradeRepository;
import me.june.academy.domain.grade.repository.GradeSearch;
import me.june.academy.domain.grade.web.GradeForm;
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
class GradeServiceTest {

    @Autowired GradeService gradeService;
    @Autowired GradeRepository gradeRepository;

    @Test
    public void 학년_목록_조회() throws Exception {
        // given
        gradeRepository.save(new Grade("gradeA"));
        gradeRepository.save(new Grade("gradeB"));
        gradeRepository.save(new Grade("gradeC"));

        GradeSearch gradeSearch = new GradeSearch();
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Grade> page = gradeService.findAll(gradeSearch, pageRequest);
        List<Grade> grades = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(grades.size()).isEqualTo(3);
    }

    @Test
    public void 학년_목록_조회_gradeA검색() throws Exception {
        // given
        gradeRepository.save(new Grade("gradeA"));
        gradeRepository.save(new Grade("gradeB"));
        gradeRepository.save(new Grade("gradeC"));

        GradeSearch gradeSearch = new GradeSearch("gradeA");
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Grade> page = gradeService.findAll(gradeSearch, pageRequest);
        List<Grade> grades = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(grades.size()).isEqualTo(1);
        assertThat(grades)
                .extracting("name")
                .containsExactly("gradeA");
    }

    @Test
    public void 학년_생성() throws Exception {
        // given
        GradeForm gradeForm = new GradeForm("gradeA");

        // when
        Long savedGradeId = gradeService.saveGrade(gradeForm);
        Optional<Grade> optionalGrade = gradeRepository.findById(savedGradeId);
        Grade findGrade = optionalGrade.get();

        // then
        assertThat(findGrade.getName()).isEqualTo(gradeForm.getName());
    }

    @Test
    public void 학년_상세_조회_성공() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));

        // when
        Grade findGrade = gradeService.findGrade(savedGrade.getId());

        // then
        assertThat(findGrade).isEqualTo(savedGrade);
        assertThat(findGrade.getName()).isEqualTo(savedGrade.getName());
    }

    @Test
    public void 학년_상세_조회_실패_존재하지_않는_반() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Long savedGradeId = savedGrade.getId();
        // when
        NotFoundGradeException exception = Assertions.assertThrows(NotFoundGradeException.class, () -> {
            gradeService.findGrade(savedGradeId + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 반 입니다.");
    }

    @Test
    public void 학년_상세_조회_실패_id값_null() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Long savedGradeId = savedGrade.getId();


        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gradeService.findGrade(null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Grade id should be not null");
    }

    @Test
    public void 학년_수정_성공() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Long savedGradeId = savedGrade.getId();

        GradeForm gradeForm = new GradeForm("gradeA수정");
        gradeForm.setId(savedGradeId);

        // when
        gradeService.updateGrade(gradeForm);
        Optional<Grade> optionalGrade = gradeRepository.findById(savedGradeId);
        Grade findGrade = optionalGrade.get();

        // then
        assertThat(findGrade.getName()).isEqualTo(gradeForm.getName());
    }

    @Test
    public void 학년_수정_실패_존재하지_않는_반() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Long savedGradeId = savedGrade.getId();

        GradeForm gradeForm = new GradeForm("gradeA수정");
        gradeForm.setId(savedGradeId + 1);

        // when
        NotFoundGradeException exception = Assertions.assertThrows(NotFoundGradeException.class, () -> {
            gradeService.updateGrade(gradeForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 반 입니다.");
    }
    
    @Test
    public void 학년_수정_실패_id값_null() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Long savedGradeId = savedGrade.getId();

        GradeForm gradeForm = new GradeForm("gradeA수정");
        gradeForm.setId(null);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gradeService.updateGrade(gradeForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Grade id should be not null");
    }

    @Test
    public void 학년_수정_실패_status_disabled() throws Exception {
        // given
        Grade grade = new Grade("gradeA");
        grade.disable();

        Grade savedGrade = gradeRepository.save(grade);
        Long savedGradeId = savedGrade.getId();

        GradeForm gradeForm = new GradeForm("gradeA수정");
        gradeForm.setId(savedGradeId);

        // when
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            gradeService.updateGrade(gradeForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("비활성화 처리된 반의 정보는 수정할 수 없습니다.");
    }

}