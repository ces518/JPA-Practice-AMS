package me.june.academy.domain.grade.repository;

import me.june.academy.domain.grade.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-19
 * Time: 17:21
 **/
public interface GradeRepositoryCustom {
    Page<Grade> findAll(GradeSearch gradeSearch, Pageable pageable);
}
