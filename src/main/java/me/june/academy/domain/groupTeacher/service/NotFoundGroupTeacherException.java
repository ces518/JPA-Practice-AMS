package me.june.academy.domain.groupTeacher.service;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-04-12
 * Time: 18:48
 **/
public class NotFoundGroupTeacherException extends RuntimeException {

    public NotFoundGroupTeacherException() {
    }

    public NotFoundGroupTeacherException(String message) {
        super(message);
    }

    public NotFoundGroupTeacherException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundGroupTeacherException(Throwable cause) {
        super(cause);
    }

    public NotFoundGroupTeacherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
