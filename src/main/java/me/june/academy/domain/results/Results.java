package me.june.academy.domain.results;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.june.academy.domain.member.Member;
import me.june.academy.domain.subject.Subject;
import me.june.academy.domain.testType.TestType;
import me.june.academy.model.BaseCreatedEntity;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:14
 **/
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Results extends BaseCreatedEntity {

    @Id @GeneratedValue
    @Column(name = "RESULT_ID")
    private Long id;

    private int score;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "TEST_TYPE_ID")
    private TestType testType;

    @Builder
    public Results(int score, Member member, Subject subject, TestType testType) {
        this.score = score;
        this.member = member;
        this.subject = subject;
        this.testType = testType;
    }

    public void update(Results results) {
        this.score = results.getScore();
        this.member = results.getMember();
        this.subject = results.getSubject();
        this.testType = results.getTestType();
    }
}
