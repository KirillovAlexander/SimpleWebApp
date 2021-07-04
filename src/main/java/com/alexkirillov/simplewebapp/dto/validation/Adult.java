package com.alexkirillov.simplewebapp.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdultValidator.class)
public @interface Adult {
    public String message() default "Employee must be adult.";

    public Class<?>[] groups() default {};
    public Class<? extends Payload> [] payload() default {};
}
