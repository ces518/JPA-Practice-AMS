package me.june.academy.domain.grade.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.june.academy.domain.grade.Grade;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-19
 * Time: 17:05
 **/
@Getter @Setter
@NoArgsConstructor
public class GradeForm {

    private Long id;
    private String name;

    public GradeForm(Grade grade) {
        this(grade.getName());
    }

    public GradeForm(String name) {
        this.name = name;
    }

    public Grade toEntity() {
        return new Grade(name);
    }
}
