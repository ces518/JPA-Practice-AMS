package me.june.academy.domain.groups.repository;

import me.june.academy.domain.groups.Groups;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 14:09
 **/
public interface GroupsRepositoryCustom {
    Page<Groups> findAll(GroupsSearch groupsSearch, Pageable pageable);
}
