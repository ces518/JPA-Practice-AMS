package me.june.academy.domain.member.repository;

import me.june.academy.domain.member.Member;
import me.june.academy.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:23
 **/
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findAllByStatus(Status status);
}
