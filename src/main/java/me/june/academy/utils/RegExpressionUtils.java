package me.june.academy.utils;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-14
 * Time: 22:00
 **/
public class RegExpressionUtils {
    public static final Pattern PHONE_PATTERN = Pattern.compile("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$");
}
