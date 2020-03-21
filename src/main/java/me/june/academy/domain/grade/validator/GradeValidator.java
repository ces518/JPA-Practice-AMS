package me.june.academy.domain.grade.validator;

import me.june.academy.domain.grade.web.GradeForm;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-21
 * Time: 23:52
 **/
public class GradeValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return GradeForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        GradeForm form = (GradeForm) o;

        if (!StringUtils.hasText(form.getName()) || form.getName().length() > 80) {
            errors.rejectValue("name", "reject", "학년 이름은 빈 값이 오거나 80자를 넘을 수 없습니다.");
        }
    }
}
