package com.theodo.albeniz.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotChildrenSongValidation implements ConstraintValidator<NotChildrenSong,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !s.toLowerCase().equals("chantal g.");
    }
}
