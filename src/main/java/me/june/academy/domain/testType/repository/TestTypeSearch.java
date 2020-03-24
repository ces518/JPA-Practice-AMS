package me.june.academy.domain.testType.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 23:06
 **/
@Getter @Setter
@NoArgsConstructor
public class TestTypeSearch {
    private String name;

    public TestTypeSearch(String name) {
        this.name = name;
    }
}
