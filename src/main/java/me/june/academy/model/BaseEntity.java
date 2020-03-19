package me.june.academy.model;

import lombok.Getter;
import me.june.academy.domain.member.Member;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 22:42
 **/
@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private Member createdBy;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "UPDATED_BY")
    private Member updatedBy;
}
