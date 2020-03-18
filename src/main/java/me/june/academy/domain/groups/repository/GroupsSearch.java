package me.june.academy.domain.groups.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 14:09
 **/
@Getter @Setter
@NoArgsConstructor
public class GroupsSearch {

    private String name;

    public GroupsSearch(String name) {
        this.name = name;
    }
}
