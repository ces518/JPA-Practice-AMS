package me.june.academy.domain.results.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class ResultsForm {

    @Getter @Setter
    @NoArgsConstructor
    public static class CreateRequest {
        private int score;
        private Long memberId;
        private Long subjectId;
        private Long testTypeId;

        public CreateRequest(int score, Long memberId, Long subjectId, Long testTypeId) {
            this.score = score;
            this.memberId = memberId;
            this.subjectId = subjectId;
            this.testTypeId = testTypeId;
        }

        public Results toEntity(Member member, Subject subject, TestType testType) {
            return Results.builder()
                    .score(this.score)
                    .member(member)
                    .subject(subject)
                    .testType(testType)
                    .build();
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class UpdateRequest {
        private Long id;
        private int score;

        public UpdateRequest(Long id, int score) {
            this.id = id;
            this.score = score;
        }

        public Results toEntity() {
            return Results.builder()
                    .score(this.score)
                    .build();
        }
    }
}
