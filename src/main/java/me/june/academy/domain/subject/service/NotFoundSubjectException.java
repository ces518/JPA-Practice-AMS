package me.june.academy.domain.subject.service;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 11:05
 **/
public class NotFoundSubjectException extends RuntimeException {

    public NotFoundSubjectException() {
    }

    public NotFoundSubjectException(String message) {
        super(message);
    }

    public NotFoundSubjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundSubjectException(Throwable cause) {
        super(cause);
    }

    public NotFoundSubjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
