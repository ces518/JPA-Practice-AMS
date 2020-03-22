package me.june.academy.domain.groups.repository;

import me.june.academy.domain.groups.Groups;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:23
 **/
public interface GroupsRepository extends JpaRepository<Groups, Long>, GroupsRepositoryCustom {

    @EntityGraph(attributePaths = "grade")
    Optional<Groups> findWithGradeById(Long id);
}
