package me.june.academy.domain.groups.service;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 13:59
 **/
public class NotFoundGroupsException extends RuntimeException {

    public NotFoundGroupsException() {
    }

    public NotFoundGroupsException(String message) {
        super(message);
    }

    public NotFoundGroupsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundGroupsException(Throwable cause) {
        super(cause);
    }

    public NotFoundGroupsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
