package me.june.academy.domain.testType.service;

import me.june.academy.common.BadRequestException;
import me.june.academy.domain.testType.TestType;
import me.june.academy.domain.testType.repository.TestTypeRepository;
import me.june.academy.domain.testType.repository.TestTypeSearch;
import me.june.academy.domain.testType.web.TestTypeForm;
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
class TestTypeServiceTest {

    @Autowired TestTypeRepository testTypeRepository;
    @Autowired TestTypeService testTypeService;

    @Test
    public void 시험타입_목록_조회() throws Exception {
        // given
        testTypeRepository.save(new TestType("testTypeA"));
        testTypeRepository.save(new TestType("testTypeB"));
        testTypeRepository.save(new TestType("testTypeC"));
        testTypeRepository.save(new TestType("testTypeD"));

        TestTypeSearch testTypeSearch = new TestTypeSearch();
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<TestType> page = testTypeService.findAll(testTypeSearch, pageRequest);
        List<TestType> testTypes = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(4);
        assertThat(testTypes.size()).isEqualTo(4);
    }

    @Test
    public void 시험타입_목록_조회_검색_testTypeA() throws Exception {
        // given
        testTypeRepository.save(new TestType("testTypeA"));
        testTypeRepository.save(new TestType("testTypeB"));
        testTypeRepository.save(new TestType("testTypeC"));
        testTypeRepository.save(new TestType("testTypeD"));

        TestTypeSearch testTypeSearch = new TestTypeSearch("testTypeA");
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<TestType> page = testTypeService.findAll(testTypeSearch, pageRequest);
        List<TestType> testTypes = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(testTypes.size()).isEqualTo(1);
        assertThat(testTypes)
                .extracting("name")
                .containsExactly("testTypeA");
    }

    @Test
    public void 시험타입_생성() throws Exception {
        // given
        TestTypeForm testTypeForm = new TestTypeForm("testTypeA");

        // when
        Long savedTestTypeId = testTypeService.saveTestType(testTypeForm);
        Optional<TestType> optionalTestType = testTypeRepository.findById(savedTestTypeId);
        TestType findTestType = optionalTestType.get();

        // then
        assertThat(findTestType.getName()).isEqualTo("testTypeA");
        assertThat(findTestType.getStatus()).isEqualTo(Status.AVAILABLE);
    }

    @Test
    public void 시험타입_조회_성공() throws Exception {
        // given
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));

        // when
        TestType findTestType = testTypeService.findTestType(savedTestType.getId());

        // then
        assertThat(findTestType).isEqualTo(savedTestType);
        assertThat(findTestType.getName()).isEqualTo(savedTestType.getName());
    }
    
    @Test
    public void 시험타입_조회_실패_존재하지_않는_시험타입() throws Exception {
        // given
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));

        // when
        NotFoundTestTypeException exception = Assertions.assertThrows(NotFoundTestTypeException.class, () -> {
            TestType findTestType = testTypeService.findTestType(savedTestType.getId() + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 시험타입 입니다.");
    }

    @Test
    public void 시험타입_조회_실패_id값_null() throws Exception {
        // given
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            TestType findTestType = testTypeService.findTestType(null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("TestType id should be not null");
    }

    @Test
    public void 시험타입_수정_성공() throws Exception {
        // given
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));
        TestTypeForm testTypeForm = new TestTypeForm("testTypeA 수정");
        testTypeForm.setId(savedTestType.getId());

        // when
        testTypeService.updateTestType(testTypeForm);
        Optional<TestType> optionalTestType = testTypeRepository.findById(savedTestType.getId());
        TestType findTestType = optionalTestType.get();

        // then
        assertThat(findTestType.getName()).isEqualTo(testTypeForm.getName());
    }

    @Test
    public void 시험타입_수정_실패_존재하지_않는_시험타입() throws Exception {
        // given
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));
        TestTypeForm testTypeForm = new TestTypeForm("testTypeA 수정");
        testTypeForm.setId(savedTestType.getId() + 1);

        // when
        NotFoundTestTypeException exception = Assertions.assertThrows(NotFoundTestTypeException.class, () -> {
            testTypeService.updateTestType(testTypeForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 시험타입 입니다.");
    }

    @Test
    public void 시험타입_수정_실패_id값_null() throws Exception {
        // given
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));
        TestTypeForm testTypeForm = new TestTypeForm("testTypeA 수정");
        testTypeForm.setId(null);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testTypeService.updateTestType(testTypeForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("TestType id should be not null");
    }

    @Test
    public void 시험타입_수정_실패_status_disabled() throws Exception {
        // given
        TestType testType = new TestType("testTypeA");
        testType.disabled();
        TestType savedTestType = testTypeRepository.save(testType);

        TestTypeForm testTypeForm = new TestTypeForm("testTypeA 수정");
        testTypeForm.setId(savedTestType.getId());

        // when
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            testTypeService.updateTestType(testTypeForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("비활성화 상태인 시험 타입의 정보는 수정할 수 없습니다.");
    }

    @Test
    public void 시험타입_삭제_성공() throws Exception {
        // given
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));

        // when
        testTypeService.deleteTestType(savedTestType.getId());
        Optional<TestType> optionalTestType = testTypeRepository.findById(savedTestType.getId());
        TestType findTestType = optionalTestType.get();

        // then
        assertThat(findTestType.getStatus()).isEqualTo(Status.DISABLED);
    }

    @Test
    public void 시험타입_삭제_실패_존재하지_않는_시험타입() throws Exception {
        // given
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));

        // when
        NotFoundTestTypeException exception = Assertions.assertThrows(NotFoundTestTypeException.class, () -> {
            testTypeService.deleteTestType(savedTestType.getId() + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 시험타입 입니다.");
    }

    @Test
    public void 시험타입_삭제_실패_id값_null() throws Exception {
        // given
        TestType savedTestType = testTypeRepository.save(new TestType("testTypeA"));

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testTypeService.deleteTestType(null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("TestType id should be not null");
    }

    @Test
    public void 시험타입_삭제_실패_status_disabled() throws Exception {
        // given
        TestType testType = new TestType("testTypeA");
        testType.disabled();

        TestType savedTestType = testTypeRepository.save(testType);

        // when
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            testTypeService.deleteTestType(savedTestType.getId());
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("이미 비활성화 된 시험 타입 입니다.");
    }
}