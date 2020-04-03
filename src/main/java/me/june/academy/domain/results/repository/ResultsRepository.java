package me.june.academy.domain.results.repository;

import me.june.academy.domain.results.Results;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:23
 **/
public interface ResultsRepository extends JpaRepository<Results, Long>, ResultsRepositoryCustom {

    @EntityGraph(attributePaths = { "testType", "subject", "member" })
    Optional<Results> findResultsById(Long id);
}
