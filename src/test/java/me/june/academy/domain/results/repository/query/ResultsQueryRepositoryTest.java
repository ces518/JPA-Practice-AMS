package me.june.academy.domain.results.repository.query;

import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.repository.MemberRepository;
import me.june.academy.domain.results.Results;
import me.june.academy.domain.results.repository.ResultsRepository;
import me.june.academy.domain.results.repository.ResultsSearch;
import me.june.academy.domain.subject.Subject;
import me.june.academy.domain.subject.repository.SubjectRepository;
import me.june.academy.domain.testType.TestType;
import me.june.academy.domain.testType.repository.TestTypeRepository;
import me.june.academy.model.Address;
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
class ResultsQueryRepositoryTest {

    @Autowired ResultsQueryRepository queryRepository;
    @Autowired ResultsRepository resultsRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired SubjectRepository subjectRepository;
    @Autowired TestTypeRepository testTypeRepository;

    @BeforeEach
    public void before() {
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
    }

    @Test
    public void findAll_no_search() throws Exception {
        // given
        ResultsSearch resultsSearch = new ResultsSearch();
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<ResultsQueryDto> page = queryRepository.findAll(resultsSearch, pageRequest);
        List<ResultsQueryDto> results = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(results.size()).isEqualTo(3);
    }

    @Test
    public void findAll_search_memberA() throws Exception {
        // given
        ResultsSearch resultsSearch = new ResultsSearch("memberA", null, null);
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<ResultsQueryDto> page = queryRepository.findAll(resultsSearch, pageRequest);
        List<ResultsQueryDto> results = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(results.size()).isEqualTo(3);
    }

    @Test
    public void findAll_search_subjectC() throws Exception {
        // given
        ResultsSearch resultsSearch = new ResultsSearch(null, "subjectC", null);
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<ResultsQueryDto> page = queryRepository.findAll(resultsSearch, pageRequest);
        List<ResultsQueryDto> results = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(results.size()).isEqualTo(1);
        assertThat(results).extracting("subjectName")
                .containsExactly("subjectC");
    }

    @Test
    public void findAll_search_testTypeA() throws Exception {
        // given
        ResultsSearch resultsSearch = new ResultsSearch(null, null, "testTypeA");
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<ResultsQueryDto> page = queryRepository.findAll(resultsSearch, pageRequest);
        List<ResultsQueryDto> results = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(results.size()).isEqualTo(3);
    }
}