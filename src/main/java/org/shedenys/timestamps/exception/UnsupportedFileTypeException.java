package org.shedenys.timestamps.exception;

/**
 * This exception is thrown to indicate that a file type is unsupported during
 * operations such as metadata extraction or file creation.
 */
public class UnsupportedFileTypeException extends RuntimeException {

    /**
     * Constructs a new {@code UnsupportedFileTypeException} with a default error message.
     */
    public UnsupportedFileTypeException() {
        super("Unsupported file type.");
    }
}
