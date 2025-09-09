package org.shedenys.timestamps.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.shedenys.timestamps.validation.constraint.NotEmptyFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * Validator for the {@link NotEmptyFile} constraint.
 */
public class NotEmptyFileValidator implements ConstraintValidator<NotEmptyFile, MultipartFile> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file != null && !file.isEmpty();
    }
}
