package me.june.academy.domain.grade.repository;

import me.june.academy.domain.grade.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:22
 **/
public interface GradeRepository extends JpaRepository<Grade, Long>, GradeRepositoryCustom {
}
