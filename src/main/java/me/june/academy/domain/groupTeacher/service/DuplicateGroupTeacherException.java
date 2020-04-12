package me.june.academy.domain.groupTeacher.service;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-04-12
 * Time: 18:46
 **/
public class DuplicateGroupTeacherException extends RuntimeException {

    public DuplicateGroupTeacherException() {
    }

    public DuplicateGroupTeacherException(String message) {
        super(message);
    }

    public DuplicateGroupTeacherException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateGroupTeacherException(Throwable cause) {
        super(cause);
    }

    public DuplicateGroupTeacherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
