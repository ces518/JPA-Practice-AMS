package me.june.academy.domain.groups.validator;

import me.june.academy.domain.groups.web.GroupsForm;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 19:49
 **/
public class GroupsValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return GroupsForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        GroupsForm form = (GroupsForm) o;

        if (!StringUtils.hasText(form.getName()) || form.getName().length() > 80) {
            errors.rejectValue("name", "reject", "이름은 빈 값이 오거나 80자를 넘을 수 없습니다.");
        }
    }
}
