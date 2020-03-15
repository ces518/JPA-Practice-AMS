package me.june.academy.domain.teacher;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.june.academy.model.BaseEntity;
import me.june.academy.model.Status;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:07
 **/
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teacher extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "TEACHER_ID")
    private Long id;

    private String name;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Teacher(String name, String phone) {
        this(name, phone, Status.AVAILABLE);
    }

    public Teacher(String name, String phone, Status status) {
        this.name = name;
        this.phone = phone;
        this.status = status;
    }

    public void update(Teacher teacher) {
        this.name = teacher.getName();
        this.phone = teacher.getPhone();
    }

    public void disable() {
        this.status = Status.DISABLED;
    }

    public boolean isAvailable() {
        return this.status == Status.AVAILABLE;
    }

    public boolean isDisabled() {
        return !isAvailable();
    }


}
