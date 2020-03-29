package me.june.academy.domain.subject.repository;

import me.june.academy.domain.subject.Subject;
import me.june.academy.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:24
 **/
public interface SubjectRepository extends JpaRepository<Subject, Long>, SubjectRepositoryCustom {

    List<Subject> findAllByStatus(Status status);
}
