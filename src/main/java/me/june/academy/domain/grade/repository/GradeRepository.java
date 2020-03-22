package me.june.academy.domain.grade.repository;

import me.june.academy.domain.grade.Grade;
import me.june.academy.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:22
 **/
public interface GradeRepository extends JpaRepository<Grade, Long>, GradeRepositoryCustom {
    List<Grade> findAllByStatus(Status available);
}
