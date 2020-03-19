package me.june.academy.domain.grade.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.june.academy.domain.grade.Grade;
import me.june.academy.domain.grade.QGrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

import static me.june.academy.domain.grade.QGrade.grade;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-19
 * Time: 17:22
 **/
public class GradeRepositoryImpl extends QuerydslRepositorySupport implements GradeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public GradeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Grade.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Grade> findAll(GradeSearch gradeSearch, Pageable pageable) {
        JPAQuery<Grade> query = queryFactory
                .select(grade)
                .from(grade)
                .where(likeName(gradeSearch.getName()));
        List<Grade> grades = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(grades, pageable, query.fetchCount());
    }

    private BooleanExpression likeName(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        return grade.name.like(name + "%");
    }
}
