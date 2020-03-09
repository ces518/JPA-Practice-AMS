package me.june.academy.domain.subject.repository;

import me.june.academy.domain.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:24
 **/
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
