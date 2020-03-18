package me.june.academy.domain.member.repository;

import me.june.academy.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-14
 * Time: 19:55
 **/
public interface MemberRepositoryCustom {
    Page<Member> findAll(MemberSearch memberSearch, Pageable pageable);

    List<Member> findAllByIdNotIn(List<Long> ids);
}
