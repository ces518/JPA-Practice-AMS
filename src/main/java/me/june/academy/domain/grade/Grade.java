package me.june.academy.domain.grade;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.june.academy.model.BaseEntity;
import me.june.academy.model.Status;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:12
 **/
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Grade extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "GRADE_ID")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Grade(String name) {
        this(name, Status.AVAILABLE);
    }

    public Grade(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public void update(Grade grade) {
        this.name = grade.getName();
    }

    public boolean isAvailable() {
        return this.status == Status.AVAILABLE;
    }

    public boolean isDisabled() {
        return !isAvailable();
    }

    public void disable() {
        this.status = Status.DISABLED;
    }
}
