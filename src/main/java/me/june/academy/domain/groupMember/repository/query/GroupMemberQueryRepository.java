package me.june.academy.domain.groupMember.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static me.june.academy.domain.groupMember.QGroupMember.groupMember;
import static me.june.academy.domain.groups.QGroups.groups;
import static me.june.academy.domain.member.QMember.member;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 20:27
 **/
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupMemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<GroupMemberQueryDto> findAllByGroupsId(Long groupsId) {
        Assert.notNull(groupsId, "Groups id should be not null");
        return queryFactory
                .select(new QGroupMemberQueryDto(groupMember.id, groups.id, member.id, member.name))
                .from(groupMember)
                .join(groupMember.group, groups)
                .join(groupMember.member, member)
                .where(groupMember.group.id.eq(groupsId))
                .orderBy(groupMember.id.asc())
                .fetch();
    }
}
