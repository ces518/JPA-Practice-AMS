package me.june.academy.domain.member.web;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.june.academy.domain.member.Member;
import me.june.academy.model.Address;
import me.june.academy.model.Status;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-11
 * Time: 18:03
 **/
@Getter @Setter
@NoArgsConstructor
public class MemberForm {

    private Long id;

    private String name;
    private String phone;

    private String city;
    private String street;
    private String zipcode;

    public MemberForm(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.phone = member.getPhone();
        this.city = member.getAddress().getCity();
        this.street = member.getAddress().getStreet();
        this.zipcode = member.getAddress().getZipcode();
    }

    @Builder
    public MemberForm(Long id, String name, String phone, String city, String street, String zipcode) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .phone(this.phone)
                .address(new Address(this.city, this.street, this.zipcode))
                .build();
    }
}
