package br.com.example.deliveryservice.infra.security.validator.key;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class KeyValidator implements ConstraintValidator<ValidKey, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("^[A-Z][A-Z0-9]*(_?[A-Z][A-Z0-9]*){0,10}$");
        return pattern.matcher(value).matches();
    }
}
