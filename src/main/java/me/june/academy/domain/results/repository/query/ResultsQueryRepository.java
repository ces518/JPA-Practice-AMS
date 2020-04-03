package me.june.academy.domain.results.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.june.academy.common.Querydsl4RepositorySupport;
import me.june.academy.domain.member.QMember;
import me.june.academy.domain.results.QResults;
import me.june.academy.domain.results.Results;
import me.june.academy.domain.results.repository.ResultsSearch;
import me.june.academy.domain.subject.QSubject;
import me.june.academy.domain.testType.QTestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static me.june.academy.domain.member.QMember.member;
import static me.june.academy.domain.results.QResults.results;
import static me.june.academy.domain.subject.QSubject.subject;
import static me.june.academy.domain.testType.QTestType.testType;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-04-03
 * Time: 19:23
 **/
@Repository
@Transactional(readOnly = true)
public class ResultsQueryRepository extends Querydsl4RepositorySupport {

    public ResultsQueryRepository() {
        super(Results.class);
    }

    // 목록조회
    public Page<ResultsQueryDto> findAll(ResultsSearch resultsSearch, Pageable pageable) {
        return applyPagination(pageable, query -> query.
                select(new QResultsQueryDto(results.id, testType.name, member.name, subject.name, results.score))
                .from(results)
                .join(results.testType, testType)
                .join(results.member, member)
                .join(results.subject, subject)
                .where(
                        likeMemberName(resultsSearch.getMemberName()),
                        likeSubjectName(resultsSearch.getSubjectName()),
                        likeTestTypeName(resultsSearch.getTestTypeName())
                )
                .orderBy(results.id.desc())
        );
    }

    private BooleanExpression likeMemberName(String memberName) {
        if (!StringUtils.hasText(memberName)) {
            return null;
        }
        return member.name.like(memberName + "%");
    }

    private BooleanExpression likeSubjectName(String subjectName) {
        if (!StringUtils.hasText(subjectName)) {
            return null;
        }
        return subject.name.like(subjectName + "%");
    }

    private BooleanExpression likeTestTypeName(String testTypeName) {
        if (!StringUtils.hasText(testTypeName)) {
            return null;
        }
        return testType.name.like(testTypeName + "%");
    }
}
