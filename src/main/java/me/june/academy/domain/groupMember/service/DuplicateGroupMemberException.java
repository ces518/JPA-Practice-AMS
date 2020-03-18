package me.june.academy.domain.groupMember.service;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 23:43
 **/
public class DuplicateGroupMemberException extends RuntimeException {

    public DuplicateGroupMemberException() {
    }

    public DuplicateGroupMemberException(String message) {
        super(message);
    }

    public DuplicateGroupMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateGroupMemberException(Throwable cause) {
        super(cause);
    }

    public DuplicateGroupMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
