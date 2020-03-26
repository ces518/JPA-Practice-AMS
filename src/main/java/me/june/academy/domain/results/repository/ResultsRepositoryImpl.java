package me.june.academy.domain.results.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.june.academy.domain.member.QMember;
import me.june.academy.domain.results.QResults;
import me.june.academy.domain.results.Results;
import me.june.academy.domain.subject.QSubject;
import me.june.academy.domain.testType.QTestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

import static me.june.academy.domain.member.QMember.member;
import static me.june.academy.domain.results.QResults.results;
import static me.june.academy.domain.subject.QSubject.subject;
import static me.june.academy.domain.testType.QTestType.testType;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-26
 * Time: 22:13
 **/
public class ResultsRepositoryImpl extends QuerydslRepositorySupport implements ResultsRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ResultsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Results.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Results> findAll(ResultsSearch resultsSearch, Pageable pageable) {
        JPAQuery<Results> query = queryFactory
                .select(results)
                .from(results)
                .leftJoin(results.member, member)
                .leftJoin(results.subject, subject)
                .leftJoin(results.testType, testType)
                .where(
                        likeMemberName(resultsSearch.getMemberName()),
                        likeSubjectName(resultsSearch.getSubjectName()),
                        likeTestTypeName(resultsSearch.getTestTypeName())
                );
        List<Results> results = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(results, pageable, query.fetchCount());
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
