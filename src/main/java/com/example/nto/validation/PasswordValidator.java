package com.example.nto.validation;

import com.example.nto.controller.dto.AuthRequestDTO;
import com.example.nto.controller.dto.UserDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, AuthRequestDTO> {
    @Override
    public boolean isValid(AuthRequestDTO dto, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        for (int i = 0; i < dto.password().length(); i++) {
            char c = dto.password().charAt(i);
            if(!Character.isLetterOrDigit(c)){
                result = true;
            }
        }
        for (int i = 0; i < dto.username().length() - 3; i++) {
            if(dto.password().equalsIgnoreCase(dto.username().substring(i, i+3))){
                return false;
            }
        }
        return result;
    }
}
