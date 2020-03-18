package me.june.academy.domain.groups;

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
 * Time: 23:08
 **/
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Groups extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "GROUP_ID")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Groups(String name) {
        this(name, Status.AVAILABLE);
    }

    public Groups(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public void update(Groups groups) {
        this.name = groups.getName();
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
