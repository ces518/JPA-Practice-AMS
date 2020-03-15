package me.june.academy.domain.teacher.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.june.academy.domain.teacher.Teacher;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-15
 * Time: 23:05
 **/
@Getter @Setter
@NoArgsConstructor
public class TeacherForm {

    private Long id;
    private String name;
    private String phone;

    public TeacherForm(Teacher teacher) {
        this(teacher.getId(), teacher.getName(), teacher.getPhone());
    }

    public TeacherForm(String name, String phone) {
        this(null, name, phone);
    }

    public TeacherForm(Long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Teacher toEntity() {
        return Teacher.builder()
                .name(this.name)
                .phone(this.phone)
                .build();
    }
}
