package me.june.academy.domain.testType.repository;

import me.june.academy.domain.testType.TestType;
import me.june.academy.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:25
 **/
public interface TestTypeRepository extends JpaRepository<TestType, Long>, TestTypeRepositoryCustom {

    List<TestType> findAllByStatus(Status status);
}
