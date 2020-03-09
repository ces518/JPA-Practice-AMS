package me.june.academy.domain.groupTeacher;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.june.academy.domain.groups.Groups;
import me.june.academy.domain.teacher.Teacher;
import me.june.academy.model.BaseCreatedEntity;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:09
 **/
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupTeacher extends BaseCreatedEntity {

    @Id @GeneratedValue
    @Column(name = "GROUP_TEACHER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Groups group;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;
}
