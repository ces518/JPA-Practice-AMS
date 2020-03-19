package me.june.academy.domain.grade.service;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-19
 * Time: 17:09
 **/
public class NotFoundGradeException extends RuntimeException {

    public NotFoundGradeException() {
    }

    public NotFoundGradeException(String message) {
        super(message);
    }

    public NotFoundGradeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundGradeException(Throwable cause) {
        super(cause);
    }

    public NotFoundGradeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
