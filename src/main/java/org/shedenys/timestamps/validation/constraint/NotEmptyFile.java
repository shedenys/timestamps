package org.shedenys.timestamps.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.shedenys.timestamps.validation.validator.NotEmptyFileValidator;

import java.lang.annotation.*;

/**
 * Annotation that can be used to validate that a file is not empty.
 */
@Documented
@Constraint(validatedBy = NotEmptyFileValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyFile {

    /**
     * The error message template.
     */
    String message() default "File can't be empty";

    /**
     * The validation groups.
     */
    Class<?>[] groups() default {};

    /**
     * The payload associated with the constraint.
     */
    Class<? extends Payload>[] payload() default {};
}
