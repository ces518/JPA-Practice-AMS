package me.june.academy.domain.groups.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.june.academy.domain.groups.Groups;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

import static me.june.academy.domain.groups.QGroups.groups;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 14:10
 **/
public class GroupsRepositoryImpl extends QuerydslRepositorySupport implements GroupsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public GroupsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Groups.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Groups> findAll(GroupsSearch groupsSearch, Pageable pageable) {
        JPAQuery<Groups> query = queryFactory
                .select(groups)
                .from(groups)
                .where(likeName(groupsSearch.getName()))
                .orderBy(groups.id.desc());
        List<Groups> groups = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(groups, pageable, query.fetchCount());
    }

    private BooleanExpression likeName(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        return groups.name.like(name + "%");
    }
}
