package me.june.academy.domain.teacher.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-16
 * Time: 00:59
 **/
@Getter @Setter
@NoArgsConstructor
public class TeacherSearch {

    private String name;

    public TeacherSearch(String name) {
        this.name = name;
    }
}
