package me.june.academy.domain.results.repository;

import me.june.academy.domain.results.Results;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:23
 **/
public interface ResultsRepository extends JpaRepository<Results, Long> {
}
