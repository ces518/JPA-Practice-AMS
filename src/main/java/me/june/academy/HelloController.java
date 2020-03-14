package me.june.academy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-14
 * Time: 21:33
 **/
@Controller
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
