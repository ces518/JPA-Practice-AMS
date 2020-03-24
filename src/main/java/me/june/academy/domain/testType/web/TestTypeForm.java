package me.june.academy.domain.testType.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.june.academy.domain.testType.TestType;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 23:00
 **/
@Getter @Setter
@NoArgsConstructor
public class TestTypeForm {
    private Long id;
    private String name;

    public TestTypeForm(TestType testType) {
        this(testType.getName());
    }

    public TestTypeForm(String name) {
        this.name = name;
    }

    public TestType toEntity() {
        return new TestType(this.name);
    }
}
