package me.june.academy.domain.groups.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.june.academy.domain.groups.Groups;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 14:01
 **/
@Getter @Setter
@NoArgsConstructor
public class GroupsForm {

    private Long id;
    private String name;

    public GroupsForm(Groups groups) {
        this(groups.getName());
    }

    public GroupsForm(String name) {
        this.name = name;
    }

    public Groups toEntity() {
        return new Groups(this.name);
    }
}
