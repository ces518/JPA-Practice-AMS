package me.june.academy.domain.groupMember.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 20:28
 **/
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMemberQueryDto {

    private Long groupMemberId;
    private Long groupsId;
    private Long memberId;
    private String memberName;

    @QueryProjection
    public GroupMemberQueryDto(Long groupMemberId, Long groupsId, Long memberId, String memberName) {
        this.groupMemberId = groupMemberId;
        this.groupsId = groupsId;
        this.memberId = memberId;
        this.memberName = memberName;
    }
}
