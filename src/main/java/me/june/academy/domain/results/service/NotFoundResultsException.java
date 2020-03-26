package me.june.academy.domain.results.service;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-26
 * Time: 22:01
 **/
public class NotFoundResultsException extends RuntimeException {

    public NotFoundResultsException() {
    }

    public NotFoundResultsException(String message) {
        super(message);
    }

    public NotFoundResultsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundResultsException(Throwable cause) {
        super(cause);
    }

    public NotFoundResultsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
