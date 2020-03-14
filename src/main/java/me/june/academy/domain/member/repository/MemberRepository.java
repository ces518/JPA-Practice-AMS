package me.june.academy.domain.member.repository;

import me.june.academy.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:23
 **/
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

}
