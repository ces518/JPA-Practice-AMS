package me.june.academy.domain.groupTeacher.repository;

import me.june.academy.domain.groupMember.GroupMember;
import me.june.academy.domain.groupTeacher.GroupTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:23
 **/
public interface GroupTeacherRepository extends JpaRepository<GroupTeacher, Long> {
    Optional<GroupTeacher> findByGroupIdAndTeacherId(Long groupsId, Long teacherId);
}
