package me.june.academy.domain.testType.service;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 22:59
 **/
public class NotFoundTestTypeException extends RuntimeException {

    public NotFoundTestTypeException() {
    }

    public NotFoundTestTypeException(String message) {
        super(message);
    }

    public NotFoundTestTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundTestTypeException(Throwable cause) {
        super(cause);
    }

    public NotFoundTestTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
