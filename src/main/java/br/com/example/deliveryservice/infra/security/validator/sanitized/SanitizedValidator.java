package br.com.example.deliveryservice.infra.security.validator.sanitized;

import static java.util.Objects.isNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class SanitizedValidator implements ConstraintValidator<SanitizedString, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("^[- ,.0-9A-Za-zÀ-ÿ]+$");

        if (isNull(value)) {
            return true;
        }

        return pattern.matcher(value).matches();
    }

}