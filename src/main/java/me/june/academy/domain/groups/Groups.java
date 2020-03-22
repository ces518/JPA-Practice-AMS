package me.june.academy.domain.groups;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.june.academy.domain.grade.Grade;
import me.june.academy.domain.groupMember.GroupMember;
import me.june.academy.model.BaseEntity;
import me.june.academy.model.Status;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GRADE_ID")
    private Grade grade;

    @OneToMany(mappedBy = "group")
    private List<GroupMember> groupMembers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    public Groups(String name, Grade grade) {
        this(name, grade, Status.AVAILABLE);
    }

    public Groups(String name, Grade grade, Status status) {
        this.name = name;
        this.grade = grade;
        this.status = status;
    }

    public void update(Groups groups) {
        this.name = groups.getName();
        this.grade = groups.grade;
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
