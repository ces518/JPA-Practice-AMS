package me.june.academy.domain.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.QMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

import static me.june.academy.domain.member.QMember.member;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-14
 * Time: 19:56
 **/
public class MemberRepositoryImpl extends QuerydslRepositorySupport implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Member.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Member> findAll(MemberSearch memberSearch, Pageable pageable) {
        JPAQuery<Member> query = queryFactory
                .select(member)
                .from(member)
                .orderBy(member.id.desc())
                .where(likeName(memberSearch.getName()));
        List<Member> members = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(members, pageable, query.fetchCount());
    }

    private BooleanExpression likeName(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        return member.name.like(name + "%");
    }
}
