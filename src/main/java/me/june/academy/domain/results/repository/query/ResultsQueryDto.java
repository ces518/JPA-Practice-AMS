package me.june.academy.domain.results.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-04-03
 * Time: 19:24
 **/
@Getter
public class ResultsQueryDto {
    private Long resultsId;
    private String testTypeName;
    private String memberName;
    private String subjectName;
    private int score;

    @QueryProjection
    public ResultsQueryDto(Long resultsId, String testTypeName, String memberName, String subjectName, int score) {
        this.resultsId = resultsId;
        this.testTypeName = testTypeName;
        this.memberName = memberName;
        this.subjectName = subjectName;
        this.score = score;
    }
}
