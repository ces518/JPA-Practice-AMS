package me.june.academy.domain.testType.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.june.academy.domain.testType.QTestType;
import me.june.academy.domain.testType.TestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

import static me.june.academy.domain.testType.QTestType.testType;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 23:07
 **/
public class TestTypeRepositoryImpl extends QuerydslRepositorySupport implements TestTypeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public TestTypeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(TestType.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public Page<TestType> findAll(TestTypeSearch testTypeSearch, Pageable pageable) {
        JPAQuery<TestType> query = queryFactory
                .select(testType)
                .from(testType)
                .where(likeName(testTypeSearch.getName()));
        List<TestType> testTypes = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(testTypes, pageable, query.fetchCount());
    }

    private BooleanExpression likeName(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        return testType.name.like(name + "%");
    }
}
