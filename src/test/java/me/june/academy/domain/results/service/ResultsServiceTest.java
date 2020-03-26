package me.june.academy.domain.results.service;

import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.repository.MemberRepository;
import me.june.academy.domain.results.Results;
import me.june.academy.domain.results.repository.ResultsRepository;
import me.june.academy.domain.results.repository.ResultsSearch;
import me.june.academy.domain.results.web.ResultsForm;
import me.june.academy.domain.subject.Subject;
import me.june.academy.domain.subject.repository.SubjectRepository;
import me.june.academy.domain.testType.TestType;
import me.june.academy.domain.testType.repository.TestTypeRepository;
import me.june.academy.model.Address;
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
class ResultsServiceTest {

    @Autowired ResultsService resultsService;
    @Autowired ResultsRepository resultsRepository;
    @Autowired TestTypeRepository testTypeRepository;
    @Autowired SubjectRepository subjectRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 성적_목록_조회() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject subjectA = subjectRepository.save(new Subject("subjectA"));
        Subject subjectB = subjectRepository.save(new Subject("subjectB"));
        Subject subjectC = subjectRepository.save(new Subject("subjectC"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));

        resultsRepository.save(new Results(100, savedMember, subjectA, savedTestType));
        resultsRepository.save(new Results(88, savedMember, subjectB, savedTestType));
        resultsRepository.save(new Results(65, savedMember, subjectC, savedTestType));

        ResultsSearch resultsSearch = new ResultsSearch();
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Results> page = resultsRepository.findAll(resultsSearch, pageRequest);
        List<Results> results = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(results.size()).isEqualTo(3);
    }

    @Test
    public void 성적_목록_조회_검색_memberA() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject subjectA = subjectRepository.save(new Subject("subjectA"));
        Subject subjectB = subjectRepository.save(new Subject("subjectB"));
        Subject subjectC = subjectRepository.save(new Subject("subjectC"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));

        resultsRepository.save(new Results(100, savedMember, subjectA, savedTestType));
        resultsRepository.save(new Results(88, savedMember, subjectB, savedTestType));
        resultsRepository.save(new Results(65, savedMember, subjectC, savedTestType));

        ResultsSearch resultsSearch = new ResultsSearch("memberA", null, null);
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Results> page = resultsService.findAll(resultsSearch, pageRequest);
        List<Results> results = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(results.size()).isEqualTo(3);
    }

    @Test
    public void 성적_목록_조회_검색_subjectC() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject subjectA = subjectRepository.save(new Subject("subjectA"));
        Subject subjectB = subjectRepository.save(new Subject("subjectB"));
        Subject subjectC = subjectRepository.save(new Subject("subjectC"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));

        resultsRepository.save(new Results(100, savedMember, subjectA, savedTestType));
        resultsRepository.save(new Results(88, savedMember, subjectB, savedTestType));
        resultsRepository.save(new Results(65, savedMember, subjectC, savedTestType));

        ResultsSearch resultsSearch = new ResultsSearch(null, "subjectC", null);
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Results> page = resultsService.findAll(resultsSearch, pageRequest);
        List<Results> results = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(results.size()).isEqualTo(1);
        assertThat(results).extracting("subject.name")
                .containsExactly("subjectC");
    }

    @Test
    public void 성적_목록_조회_검색_testTypeA() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject subjectA = subjectRepository.save(new Subject("subjectA"));
        Subject subjectB = subjectRepository.save(new Subject("subjectB"));
        Subject subjectC = subjectRepository.save(new Subject("subjectC"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));

        resultsRepository.save(new Results(100, savedMember, subjectA, savedTestType));
        resultsRepository.save(new Results(88, savedMember, subjectB, savedTestType));
        resultsRepository.save(new Results(65, savedMember, subjectC, savedTestType));

        ResultsSearch resultsSearch = new ResultsSearch(null, null, "testTypeA");
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Results> page = resultsService.findAll(resultsSearch, pageRequest);
        List<Results> results = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(results.size()).isEqualTo(3);
    }

    @Test
    public void 성적_등록() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));

        ResultsForm.CreateRequest createRequest = new ResultsForm.CreateRequest(50, savedMember.getId(), savedSubject.getId(), savedTestType.getId());

        // when
        Long savedResultsId = resultsService.saveResults(createRequest);
        Optional<Results> optionalResults = resultsRepository.findById(savedResultsId);
        Results findResults = optionalResults.get();

        // then
        assertThat(findResults.getScore()).isEqualTo(createRequest.getScore());
        assertThat(findResults.getMember()).isEqualTo(savedMember);
        assertThat(findResults.getSubject()).isEqualTo(savedSubject);
        assertThat(findResults.getTestType()).isEqualTo(savedTestType);
    }

    @Test
    public void 성적_조회_성공() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));
        Results results = new Results(100, savedMember, savedSubject, savedTestType);
        Results savedResults = resultsRepository.save(results);

        // when
        Results findResults = resultsService.findResults(savedResults.getId());

        // then
        assertThat(findResults).isEqualTo(savedResults);
        assertThat(findResults.getScore()).isEqualTo(savedResults.getScore());
        assertThat(findResults.getMember()).isEqualTo(savedResults.getMember());
        assertThat(findResults.getSubject()).isEqualTo(savedResults.getSubject());
        assertThat(findResults.getTestType()).isEqualTo(savedResults.getTestType());
    }

    @Test
    public void 성적_조회_실패_존재하지_않는_성적() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));
        Results results = new Results(100, savedMember, savedSubject, savedTestType);
        Results savedResults = resultsRepository.save(results);

        // when
        NotFoundResultsException exception = Assertions.assertThrows(NotFoundResultsException.class, () -> {
            Results findResults = resultsService.findResults(savedResults.getId() + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 성적입니다.");
    }

    @Test
    public void 성적_조회_실패_id값_null() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));
        Results results = new Results(100, savedMember, savedSubject, savedTestType);
        Results savedResults = resultsRepository.save(results);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Results findResults = resultsService.findResults(null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Results id should be not null");
    }

    @Test
    public void 성적_수정_성공() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));
        Results results = new Results(100, savedMember, savedSubject, savedTestType);
        Results savedResults = resultsRepository.save(results);

        ResultsForm.UpdateRequest updateRequest = new ResultsForm.UpdateRequest(savedResults.getId(), 50);

        // when
        resultsService.updateResults(updateRequest);
        Optional<Results> optionalResults = resultsRepository.findById(updateRequest.getId());
        Results findResults = optionalResults.get();

        // then
        assertThat(findResults.getScore()).isEqualTo(50);
    }

    @Test
    public void 성적_수정_실패_존재하지_않는_성적() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));
        Results results = new Results(100, savedMember, savedSubject, savedTestType);
        Results savedResults = resultsRepository.save(results);

        ResultsForm.UpdateRequest updateRequest = new ResultsForm.UpdateRequest(savedResults.getId() + 1, 50);

        // when
        NotFoundResultsException exception = Assertions.assertThrows(NotFoundResultsException.class, () -> {
            resultsService.updateResults(updateRequest);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 성적입니다.");
    }

    @Test
    public void 성적_수정_실패_id값_null() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));
        Results results = new Results(100, savedMember, savedSubject, savedTestType);
        Results savedResults = resultsRepository.save(results);

        ResultsForm.UpdateRequest updateRequest = new ResultsForm.UpdateRequest(null, 50);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            resultsService.updateResults(updateRequest);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Results id should be not null");
    }

    @Test
    public void 성적_삭제_성공() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));
        Results results = new Results(100, savedMember, savedSubject, savedTestType);
        Results savedResults = resultsRepository.save(results);

        // when
        resultsService.deleteResults(savedResults.getId());
        Optional<Results> optionalResults = resultsRepository.findById(savedResults.getId());

        // then
        assertThat(optionalResults.isPresent()).isFalse();
    }

    @Test
    public void 성적_삭제_실패_존재하지_않는_성적() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));
        Results results = new Results(100, savedMember, savedSubject, savedTestType);
        Results savedResults = resultsRepository.save(results);

        // when
        NotFoundResultsException exception = Assertions.assertThrows(NotFoundResultsException.class, () -> {
            resultsService.deleteResults(savedResults.getId() + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 성적입니다.");
    }

    @Test
    public void 성적_삭제_실패_id값_null() throws Exception {
        // given
        Member savedMember = memberRepository.save(new Member("memberA",
                new Address("경기도", "수원시", "1234"),
                "010-1234-1234"));
        Subject savedSubject = subjectRepository.save(new Subject("subjectA"));
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));
        Results results = new Results(100, savedMember, savedSubject, savedTestType);
        Results savedResults = resultsRepository.save(results);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            resultsService.deleteResults(null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Results id should be not null");
    }
}