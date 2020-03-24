package me.june.academy.domain.testType.repository;

import me.june.academy.domain.testType.TestType;
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
class TestTypeRepositoryTest {

    @Autowired TestTypeRepository testTypeRepository;

    @BeforeEach
    public void before() {
        testTypeRepository.save(new TestType("testTypeA"));
        testTypeRepository.save(new TestType("testTypeB"));
        testTypeRepository.save(new TestType("testTypeC"));
        testTypeRepository.save(new TestType("testTypeD"));
    }

    @Test
    public void findAll_no_search() throws Exception {
        // given
        TestTypeSearch testTypeSearch = new TestTypeSearch();
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<TestType> page = testTypeRepository.findAll(testTypeSearch, pageRequest);
        List<TestType> testTypes = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(4);
        assertThat(testTypes.size()).isEqualTo(4);
    }

    @Test
    public void findAll_search_testTypeD() throws Exception {
        // given
        TestTypeSearch testTypeSearch = new TestTypeSearch("testTypeD");
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<TestType> page = testTypeRepository.findAll(testTypeSearch, pageRequest);
        List<TestType> testTypes = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(testTypes.size()).isEqualTo(1);
        assertThat(testTypes)
                .extracting("name")
                .containsExactly("testTypeD");
    }
}