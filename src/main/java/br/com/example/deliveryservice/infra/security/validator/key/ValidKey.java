package br.com.example.deliveryservice.infra.security.validator.key;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

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
    String message() default "{IpAddress.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
