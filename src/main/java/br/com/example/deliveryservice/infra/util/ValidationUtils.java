package br.com.example.deliveryservice.infra.util;


import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationUtils {

    private ValidationUtils() {

    }

    public static String handleValidationMessage(List<FieldError> errors) {
        return errors.stream().map(ValidationUtils::formatError).collect(Collectors.joining("; "));
    }

    private static String formatError(FieldError error) {
        if (error.shouldRenderDefaultMessage()) {
            return error.getDefaultMessage();
        }

        return String.format("%s: %s", error.getField(), error.getDefaultMessage());
    }
}
