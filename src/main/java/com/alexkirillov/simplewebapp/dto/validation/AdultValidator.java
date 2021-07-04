package com.alexkirillov.simplewebapp.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class AdultValidator implements ConstraintValidator<Adult, Date> {

    private final long millisecondsToEighteenYO = 567648000007L;

    private Date currentDate;

    @Override
    public boolean isValid(Date dateOfBirth, ConstraintValidatorContext constraintValidatorContext) {
        long diffInMillis = (currentDate.getTime() - dateOfBirth.getTime());
        return diffInMillis > millisecondsToEighteenYO;
    }

    @Override
    public void initialize(Adult constraintAnnotation) {
        currentDate = new Date(System.currentTimeMillis());
    }
}
