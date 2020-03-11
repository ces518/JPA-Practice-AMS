package me.june.academy.common;

import lombok.Getter;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-11
 * Time: 18:12
 **/
@Getter
public enum Message {
    CREATED("생성되었습니다."),
    UPDATED("수정되었습니다."),
    DELETED("삭제되었습니다."),
    SUCCESS("성공하였습니다."),
    FAILED("실패하였습니다.");

    private String message;

    Message(String message) {
        this.message = message;
    }
}
