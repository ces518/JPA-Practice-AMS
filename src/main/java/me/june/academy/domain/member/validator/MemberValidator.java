package me.june.academy.domain.member.validator;

import me.june.academy.domain.member.web.MemberForm;
import me.june.academy.utils.RegExpressionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static me.june.academy.utils.RegExpressionUtils.PHONE_PATTERN;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-14
 * Time: 21:54
 **/
public class MemberValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return MemberForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MemberForm form = (MemberForm) o;

        if (!StringUtils.hasText(form.getName()) || form.getName().length() > 80) {
            errors.rejectValue("name", "reject", "이름은 빈 값이 오거나 80자를 넘을 수 없습니다.");
        }

        if (!StringUtils.hasText(form.getCity()) || form.getCity().length() > 80) {
            errors.rejectValue("city", "reject", "도시는 빈 값이 오거나 80자를 넘을 수 없습니다.");
        }

        if (!StringUtils.hasText(form.getStreet()) || form.getStreet().length() > 80) {
            errors.rejectValue("street", "reject", "거리는 빈 값이 오거나 80자를 넘을 수 없습니다.");
        }

        if (!StringUtils.hasText(form.getZipcode()) || form.getZipcode().length() > 80) {
            errors.rejectValue("zipcode", "reject", "우편번호는 빈 값이 오거나 80자를 넘을 수 없습니다.");
        }

        if (!StringUtils.hasText(form.getPhone())
                || !PHONE_PATTERN.matcher(form.getPhone()).matches()) {
            errors.rejectValue("phone", "reject", "연락처는 빈 값이 올 수 없고, 010-1234-1234 형식이여야 합니다.");
        }

    }
}
