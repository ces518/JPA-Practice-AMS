package me.june.academy.domain.subject.validator;

import me.june.academy.domain.subject.web.SubjectForm;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-24
 * Time: 22:44
 **/
public class SubjectValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return SubjectForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SubjectForm form = (SubjectForm) o;

        if (!StringUtils.hasText(form.getName()) || form.getName().length() > 80) {
            errors.rejectValue("name", "reject", "과목 명은 빈 값이 오거나 80자를 넘을 수 없습니다.");
        }
    }
}
