package me.june.academy.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.june.academy.model.Address;
import me.june.academy.model.BaseEntity;
import me.june.academy.model.Status;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:02
 **/
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Member(String name, Address address, String phone) {
        this(name, address, phone, Status.AVAILABLE);
    }

    public Member(String name, Address address, String phone, Status status) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.status = status;
    }

    public void disable() {
        this.status = Status.DISABLED;
    }

    public void update(Member member) {
        this.name = member.getName();
        this.address = member.getAddress();
        this.phone = member.getPhone();
    }

    public boolean isAvailable() {
        return this.status == Status.AVAILABLE;
    }

    public boolean isDisabled() {
        return !isAvailable();
    }
}
