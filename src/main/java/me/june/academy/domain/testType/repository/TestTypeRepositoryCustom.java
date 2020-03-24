package me.june.academy.domain.testType.repository;

import me.june.academy.domain.testType.TestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 23:06
 **/
public interface TestTypeRepositoryCustom {
    Page<TestType> findAll(TestTypeSearch testTypeSearch, Pageable pageable);
}
