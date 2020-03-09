package me.june.academy.domain.subject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.june.academy.model.BaseEntity;
import me.june.academy.model.Status;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:13
 **/
@Entity
@Getter
@NoArgsConstructor
public class Subject extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "SUBJECT_ID")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;
}
