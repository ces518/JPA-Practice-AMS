package me.june.academy.domain.results.web;

import lombok.Getter;
import lombok.Setter;
import me.june.academy.domain.member.Member;
import me.june.academy.domain.results.Results;
import me.june.academy.domain.subject.Subject;
import me.june.academy.domain.testType.TestType;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-26
 * Time: 22:01
 **/
@Getter @Setter
public class ResultsForm {
    private Long id;
    private int score;
    private Long memberId;
    private Long subjectId;
    private Long testTypeId;

    public Results toEntity(Member member, Subject subject, TestType testType) {
        return Results.builder()
                .score(this.score)
                .member(member)
                .subject(subject)
                .testType(testType)
                .build();
    }
}
