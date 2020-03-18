package me.june.academy.domain.groupMember.service;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 21:48
 **/
public class NotFoundGroupMemberException extends RuntimeException {

    public NotFoundGroupMemberException() {
    }

    public NotFoundGroupMemberException(String message) {
        super(message);
    }

    public NotFoundGroupMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundGroupMemberException(Throwable cause) {
        super(cause);
    }

    public NotFoundGroupMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
