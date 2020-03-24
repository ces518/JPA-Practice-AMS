package me.june.academy.domain.subject.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 11:13
 **/
@Getter @Setter
@NoArgsConstructor
public class SubjectSearch {
    private String name;

    public SubjectSearch(String name) {
        this.name = name;
    }
}
