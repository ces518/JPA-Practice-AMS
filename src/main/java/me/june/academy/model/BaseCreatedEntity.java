package me.june.academy.model;

import lombok.Getter;
import me.june.academy.domain.member.Member;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-09
 * Time: 23:19
 **/
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseCreatedEntity {
    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private Member createdBy;
}
