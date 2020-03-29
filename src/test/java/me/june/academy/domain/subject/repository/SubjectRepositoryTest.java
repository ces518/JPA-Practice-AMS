package me.june.academy.domain.subject.repository;

import me.june.academy.domain.subject.Subject;
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
class SubjectRepositoryTest {

    @Autowired SubjectRepository subjectRepository;

    @BeforeEach
    public void before() {
        subjectRepository.save(new Subject("subjectA"));
        subjectRepository.save(new Subject("subjectB"));
        subjectRepository.save(new Subject("subjectC"));
        subjectRepository.save(new Subject("subjectD"));
    }

    @Test
    public void findAll_no_search() throws Exception {
        // given
        SubjectSearch subjectSearch = new SubjectSearch();
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Subject> page = subjectRepository.findAll(subjectSearch, pageRequest);
        List<Subject> subjects = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(4);
        assertThat(subjects.size()).isEqualTo(4);
    }

    @Test
    public void findAll_search_subjectB() throws Exception {
        // given
        SubjectSearch subjectSearch = new SubjectSearch("subjectB");
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Subject> page = subjectRepository.findAll(subjectSearch, pageRequest);
        List<Subject> subjects = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(subjects.size()).isEqualTo(1);
        assertThat(subjects.get(0).getName()).isEqualTo("subjectB");
    }

    @Test
    public void findAllByStatus_available() throws Exception {
        // given
        Status status = Status.AVAILABLE;

        // when
        List<Subject> subjects = subjectRepository.findAllByStatus(status);

        // then
        assertThat(subjects).extracting("status")
                .contains(Status.AVAILABLE);
    }

    @Test
    public void findAllByStatus_disabled() throws Exception {
        // given
        Status status = Status.DISABLED;

        // when
        List<Subject> subjects = subjectRepository.findAllByStatus(status);

        // then
        assertThat(subjects.isEmpty()).isTrue();
    }
}