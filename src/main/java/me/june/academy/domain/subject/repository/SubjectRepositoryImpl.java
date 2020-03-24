package me.june.academy.domain.subject.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.june.academy.domain.subject.QSubject;
import me.june.academy.domain.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

import static me.june.academy.domain.subject.QSubject.subject;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 11:14
 **/
public class SubjectRepositoryImpl extends QuerydslRepositorySupport implements SubjectRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public SubjectRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Subject.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Subject> findAll(SubjectSearch subjectSearch, Pageable pageable) {
        JPAQuery<Subject> query = queryFactory
                .select(subject)
                .from(subject)
                .where(likeName(subjectSearch.getName()));
        List<Subject> subjects = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(subjects, pageable, query.fetchCount());
    }

    private BooleanExpression likeName(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        return subject.name.like(name + "%");
    }
}
