package me.june.academy.domain.teacher.repository;

import me.june.academy.domain.teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:24
 **/
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
