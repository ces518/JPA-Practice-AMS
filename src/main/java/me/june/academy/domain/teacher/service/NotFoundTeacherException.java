package me.june.academy.domain.teacher.service;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-15
 * Time: 23:00
 **/
public class NotFoundTeacherException extends RuntimeException {

    public NotFoundTeacherException() {
    }

    public NotFoundTeacherException(String message) {
        super(message);
    }

    public NotFoundTeacherException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundTeacherException(Throwable cause) {
        super(cause);
    }

    public NotFoundTeacherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
