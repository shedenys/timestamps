package org.shedenys.timestamps.validation.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link NotEmptyFileValidator} class.
 */
public class NotEmptyFileValidatorTest {
    private NotEmptyFileValidator validator;

    @BeforeEach
    void setUp() {
        validator = new NotEmptyFileValidator();
    }

    @Test
    void shouldReturnFalse_whenFileIsNull() {
        assertFalse(validator.isValid(null, null));
    }

    @Test
    void shouldReturnFalse_whenFileIsEmpty() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.isEmpty()).thenReturn(true);

        assertFalse(validator.isValid(file, null));
    }

    @Test
    void shouldReturnTrue_whenFileIsNotEmpty() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.isEmpty()).thenReturn(false);

        assertTrue(validator.isValid(file, null));
    }
}
