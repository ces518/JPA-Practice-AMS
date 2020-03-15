package me.june.academy.domain.teacher.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.june.academy.domain.teacher.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

import static me.june.academy.domain.teacher.QTeacher.teacher;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-16
 * Time: 01:00
 **/
public class TeacherRepositoryImpl extends QuerydslRepositorySupport implements TeacherRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TeacherRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Teacher.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Teacher> findAll(TeacherSearch teacherSearch, Pageable pageable) {
        JPAQuery<Teacher> query = queryFactory
                .select(teacher)
                .from(teacher)
                .where(likeName(teacherSearch.getName()))
                .orderBy(teacher.id.desc());
        List<Teacher> teachers = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(teachers, pageable, query.fetchCount());
    }

    private BooleanExpression likeName(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        return teacher.name.like(name + "%");
    }
}
