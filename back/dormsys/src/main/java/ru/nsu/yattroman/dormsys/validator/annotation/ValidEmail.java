package ru.nsu.yattroman.dormsys.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = Constants.PATTERN, flags = Pattern.Flag.CASE_INSENSITIVE)
public @interface ValidEmail {
    String message() default "Wrong email";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

interface Constants {
    String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
    String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)+";
    String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

    String PATTERN = "^" + ATOM + "+(\\." + ATOM + "+)*@" + DOMAIN + "|" + IP_DOMAIN + ")$";
}