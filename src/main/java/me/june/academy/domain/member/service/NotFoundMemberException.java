package me.june.academy.domain.member.service;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-11
 * Time: 17:58
 **/
public class NotFoundMemberException extends RuntimeException {

    public NotFoundMemberException() {
    }

    public NotFoundMemberException(String message) {
        super(message);
    }

    public NotFoundMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundMemberException(Throwable cause) {
        super(cause);
    }

    public NotFoundMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
