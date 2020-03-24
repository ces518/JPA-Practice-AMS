package me.june.academy.domain.testType.validator;

import me.june.academy.domain.testType.web.TestTypeForm;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-25
 * Time: 02:10
 **/
public class TestTypeValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return TestTypeForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TestTypeForm form = (TestTypeForm) o;

        if (!StringUtils.hasText(form.getName()) || form.getName().length() > 80) {
            errors.rejectValue("name", "reject", "시험 타입명은 빈 값이 오거나 80자를 넘을 수 없습니다.");
        }
    }
}
