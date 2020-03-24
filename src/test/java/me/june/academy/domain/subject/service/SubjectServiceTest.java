package me.june.academy.domain.subject.service;

import me.june.academy.common.BadRequestException;
import me.june.academy.domain.subject.Subject;
import me.june.academy.domain.subject.repository.SubjectRepository;
import me.june.academy.domain.subject.repository.SubjectSearch;
import me.june.academy.domain.subject.web.SubjectForm;
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
class SubjectServiceTest {

    @Autowired SubjectRepository subjectRepository;
    @Autowired SubjectService subjectService;

    @Test
    public void 과목_목록_조회() throws Exception {
        // given
        subjectRepository.save(new Subject("subjectA"));
        subjectRepository.save(new Subject("subjectB"));
        subjectRepository.save(new Subject("subjectC"));
        subjectRepository.save(new Subject("subjectD"));

        SubjectSearch subjectSearch = new SubjectSearch();
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Subject> page = subjectService.findAll(subjectSearch, pageRequest);
        List<Subject> subjects = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(4);
        assertThat(subjects.size()).isEqualTo(4);
    }
    
    @Test
    public void 과목_목록_조회_검색_subjectA() throws Exception {
        // given
        subjectRepository.save(new Subject("subjectA"));
        subjectRepository.save(new Subject("subjectB"));
        subjectRepository.save(new Subject("subjectC"));
        subjectRepository.save(new Subject("subjectD"));

        SubjectSearch subjectSearch = new SubjectSearch("subjectA");
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Subject> page = subjectService.findAll(subjectSearch, pageRequest);
        List<Subject> subjects = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(subjects.size()).isEqualTo(1);
        assertThat(subjects.get(0).getName()).isEqualTo("subjectA");
    }

    @Test
    public void 과목_생성() throws Exception {
        // given
        SubjectForm subjectForm = new SubjectForm("subjectA");

        // when
        Long savedSubjectId = subjectService.saveSubject(subjectForm);
        Optional<Subject> optionalSubject = subjectRepository.findById(savedSubjectId);
        Subject findSubject = optionalSubject.get();

        // then
        assertThat(findSubject.getName()).isEqualTo("subjectA");
    }
    
    @Test
    public void 과목_상세_조회_성공() throws Exception {
        // given
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));

        // when
        Subject findSubject = subjectService.findSubject(savedSubject.getId());

        // then
        assertThat(findSubject).isEqualTo(savedSubject);
        assertThat(findSubject.getName()).isEqualTo(savedSubject.getName());
        assertThat(findSubject.getStatus()).isEqualTo(Status.AVAILABLE);
    }

    @Test
    public void 과목_상세_조회_실패_존재하지_않는_과목() throws Exception {
        // given
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));

        // when
        NotFoundSubjectException exception = Assertions.assertThrows(NotFoundSubjectException.class, () -> {
            Subject findSubject = subjectService.findSubject(savedSubject.getId() + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 과목 입니다.");
    }

    @Test
    public void 과목_상세_조회_실패_id값_null() throws Exception {
        // given
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Subject findSubject = subjectService.findSubject(null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Subject id should be not null");
    }

    @Test
    public void 과목_수정_성공() throws Exception {
        // given
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));
        SubjectForm subjectForm = new SubjectForm(savedSubject);
        subjectForm.setId(savedSubject.getId());

        // when
        subjectService.updateSubject(subjectForm);
        Optional<Subject> optionalSubject = subjectRepository.findById(savedSubject.getId());
        Subject findSubject = optionalSubject.get();

        // then
        assertThat(findSubject.getName()).isEqualTo(subjectForm.getName());
    }

    @Test
    public void 과목_수정_실패_존재하지_않는_과목() throws Exception {
        // given
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));
        SubjectForm subjectForm = new SubjectForm(savedSubject);
        subjectForm.setId(savedSubject.getId() + 1);

        // when
        NotFoundSubjectException exception = Assertions.assertThrows(NotFoundSubjectException.class, () -> {
            subjectService.updateSubject(subjectForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 과목 입니다.");
    }

    @Test
    public void 과목_수정_실패_id값_null() throws Exception {
        // given
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));
        SubjectForm subjectForm = new SubjectForm(savedSubject);
        subjectForm.setId(null);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            subjectService.updateSubject(subjectForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Subject id should be not null");
    }

    @Test
    public void 과목_수정_실패_status_disabled() throws Exception {
        // given
        Subject subject = new Subject("subjectA");
        subject.disable();
        Subject savedSubject = subjectRepository.save(subject);

        SubjectForm subjectForm = new SubjectForm(savedSubject);
        subjectForm.setId(savedSubject.getId());

        // when
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            subjectService.updateSubject(subjectForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("비활성화 상태인 과목의 정보는 수정할 수 없습니다.");
    }

    @Test
    public void 과목_삭제_성공() throws Exception {
        // given
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));

        // when
        subjectService.deleteSubject(savedSubject.getId());
        Optional<Subject> optionalSubject = subjectRepository.findById(savedSubject.getId());
        Subject findSubject = optionalSubject.get();

        // then
        assertThat(findSubject.getStatus()).isEqualTo(Status.DISABLED);
    }

    @Test
    public void 과목_삭제_실패_존재하지_않는_과목() throws Exception {
        // given
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));

        // when
        NotFoundSubjectException exception = Assertions.assertThrows(NotFoundSubjectException.class, () -> {
            subjectService.deleteSubject(savedSubject.getId() + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 과목 입니다.");
    }

    @Test
    public void 과목_삭제_실패_id값_null() throws Exception {
        // given
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            subjectService.deleteSubject(null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Subject id should be not null");
    }

    @Test
    public void 과목_삭제_실패_status_disabled() throws Exception {
        // given
        Subject subject = new Subject("subjectA");
        subject.disable();
        Subject savedSubject = subjectRepository.save(subject);

        // when
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            subjectService.deleteSubject(savedSubject.getId());
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("이미 비활성화된 과목입니다.");
    }
}