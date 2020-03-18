package me.june.academy.domain.groupMember.repository;

import me.june.academy.domain.groupMember.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:22
 **/
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    Optional<GroupMember> findByGroupIdAndMemberId(Long groupId, Long memberId);
}
