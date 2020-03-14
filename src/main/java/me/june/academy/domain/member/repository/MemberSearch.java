package me.june.academy.domain.member.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-14
 * Time: 19:56
 **/
@Getter @Setter
@NoArgsConstructor
public class MemberSearch {
    private String name;

    public MemberSearch(String name) {
        this.name = name;
    }
}
