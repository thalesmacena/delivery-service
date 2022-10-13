package br.com.example.deliveryservice.infra.security.validator.sanitized;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Marker annotation to validate a string to prevent some XSS.
 *
 * @author Thales Macena
 * @see javax.validation.ConstraintValidator
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SanitizedValidator.class)
@Documented
public @interface SanitizedString {
    String message() default "The field cannot have special characters";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}