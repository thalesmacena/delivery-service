package br.com.example.deliveryservice.infra.security.validator.key;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Marker annotation to validate a string key field.
 *
 * @author Thales Macena
 * @see javax.validation.ConstraintValidator
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = KeyValidator.class)
@Documented
public @interface ValidKey {
    String message() default "invalid key format";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
