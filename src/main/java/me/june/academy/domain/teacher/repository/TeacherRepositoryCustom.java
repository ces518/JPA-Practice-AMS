package me.june.academy.domain.teacher.repository;

import me.june.academy.domain.teacher.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-16
 * Time: 00:58
 **/
public interface TeacherRepositoryCustom {
    Page<Teacher> findAll(TeacherSearch teacherSearch, Pageable pageable);
}
