package me.june.academy.domain.groupMember;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.june.academy.domain.groups.Groups;
import me.june.academy.domain.member.Member;
import me.june.academy.model.BaseCreatedEntity;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:10
 **/
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMember extends BaseCreatedEntity {

    @Id @GeneratedValue
    @Column(name = "GROUP_MEMBER")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Groups group;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}