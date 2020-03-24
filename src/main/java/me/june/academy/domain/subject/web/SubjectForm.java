package me.june.academy.domain.subject.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.june.academy.domain.subject.Subject;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 11:06
 **/
@Getter @Setter
@NoArgsConstructor
public class SubjectForm {
    private Long id;
    private String name;

    public SubjectForm(Subject savedSubject) {
        this(savedSubject.getName());
    }

    public SubjectForm(String name) {
        this.name = name;
    }

    public Subject toEntity() {
        return new Subject(this.name);
    }
}
