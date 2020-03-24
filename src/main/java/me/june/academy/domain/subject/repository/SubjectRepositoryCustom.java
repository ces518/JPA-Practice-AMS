package me.june.academy.domain.subject.repository;

import me.june.academy.domain.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 11:12
 **/
public interface SubjectRepositoryCustom {
    Page<Subject> findAll(SubjectSearch subjectSearch, Pageable pageable);
}
