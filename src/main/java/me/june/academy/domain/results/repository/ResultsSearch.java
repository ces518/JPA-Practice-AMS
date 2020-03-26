package me.june.academy.domain.results.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-26
 * Time: 22:12
 **/
@Getter @Setter
@NoArgsConstructor
public class ResultsSearch {
    private String memberName;
    private String subjectName;
    private String testTypeName;

    public ResultsSearch(String memberName, String subjectName, String testTypeName) {
        this.memberName = memberName;
        this.subjectName = subjectName;
        this.testTypeName = testTypeName;
    }
}
