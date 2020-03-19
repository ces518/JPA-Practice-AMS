package me.june.academy.domain.grade.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-19
 * Time: 17:21
 **/
@Getter @Setter
@NoArgsConstructor
public class GradeSearch {

    private String name;

    public GradeSearch(String name) {
        this.name = name;
    }
}
