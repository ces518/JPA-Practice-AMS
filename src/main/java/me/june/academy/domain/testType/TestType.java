package me.june.academy.domain.testType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.june.academy.model.BaseEntity;
import me.june.academy.model.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:13
 **/
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestType extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "TEST_TYPE_ID")
    private Long id;

    private String name;

    private Status status;

    public TestType(String name) {
        this(name, Status.AVAILABLE);
    }

    public TestType(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public void disabled() {
        this.status = Status.DISABLED;
    }

    public void update(TestType testType) {
        this.name = testType.getName();
    }

    public boolean isDisabled() {
        return !isAvailable();
    }

    public boolean isAvailable() {
        return this.status == Status.AVAILABLE;
    }
}
