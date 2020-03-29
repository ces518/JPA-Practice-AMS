package me.june.academy.domain.results.validator;

import me.june.academy.domain.results.web.ResultsForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-29
 * Time: 23:43
 **/
public class ResultsCreateValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ResultsForm.CreateRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ResultsForm.CreateRequest form = (ResultsForm.CreateRequest) o;

        if (form.getTestTypeId() == null) {
            errors.rejectValue("testTypeId", "reject", "시험분류는 빈 값이 올 수 없습니다.");
        }
        if (form.getSubjectId() == null) {
            errors.rejectValue("subjectId", "reject", "과목은 빈 값이 올 수 없습니다.");
        }
        if (form.getMemberId() == null) {
            errors.rejectValue("memberId", "reject", "학생은 빈 값이 올 수 없습니다.");
        }
        if (form.getScore() > 100 || form.getScore() < 0) {
            errors.rejectValue("score", "reject", "점수는 0 ~ 100 사이의 수만 가능합니다.");
        }
    }
}
