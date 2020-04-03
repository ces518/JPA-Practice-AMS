package me.june.academy.domain.results.validator;

import me.june.academy.domain.results.web.ResultsForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-04-03
 * Time: 20:17
 **/
public class ResultsUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ResultsForm.UpdateRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ResultsForm.UpdateRequest form = (ResultsForm.UpdateRequest) o;

        if (form.getScore() > 100 || form.getScore() < 0) {
            errors.rejectValue("score", "reject", "점수는 0 ~ 100 사이의 수만 가능합니다.");
        }
    }
}
