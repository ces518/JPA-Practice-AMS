package me.june.academy.domain.groupTeacher.repository;

import me.june.academy.domain.groupTeacher.GroupTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:23
 **/
public interface GroupTeacherRepository extends JpaRepository<GroupTeacher, Long> {
}
