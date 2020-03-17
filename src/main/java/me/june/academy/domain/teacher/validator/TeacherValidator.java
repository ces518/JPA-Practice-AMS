package me.june.academy.domain.teacher.validator;

import me.june.academy.domain.teacher.web.TeacherForm;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static me.june.academy.utils.RegExpressionUtils.PHONE_PATTERN;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 01:08
 **/
public class TeacherValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return TeacherForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TeacherForm form = (TeacherForm) o;

        if (!StringUtils.hasText(form.getName()) || form.getName().length() > 80) {
            errors.rejectValue("name", "reject", "이름은 빈 값이 오거나 80자를 넘을 수 없습니다.");
        }

        if (!StringUtils.hasText(form.getPhone())
                || !PHONE_PATTERN.matcher(form.getPhone()).matches()) {
            errors.rejectValue("phone", "reject", "연락처는 빈 값이 올 수 없고, 010-1234-1234 형식이여야 합니다.");
        }

    }
}
