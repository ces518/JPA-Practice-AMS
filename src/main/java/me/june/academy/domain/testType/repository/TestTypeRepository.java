package me.june.academy.domain.testType.repository;

import me.june.academy.domain.testType.TestType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:25
 **/
public interface TestTypeRepository extends JpaRepository<TestType, Long> {
}
