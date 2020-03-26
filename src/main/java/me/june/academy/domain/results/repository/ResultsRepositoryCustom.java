package me.june.academy.domain.results.repository;

import me.june.academy.domain.results.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-26
 * Time: 22:13
 **/
public interface ResultsRepositoryCustom {
    Page<Results> findAll(ResultsSearch resultsSearch, Pageable pageable);
}
