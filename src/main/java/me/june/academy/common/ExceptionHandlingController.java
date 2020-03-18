package me.june.academy.common;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 19:38
 **/
@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(RuntimeException.class)
    public String errorPage(RuntimeException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errors/error";
    }
}
