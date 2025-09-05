package org.shedenys.timestamps.exception;

/**
 * An exception that is thrown when an attempt to read metadata from a file fails.
 */
public class MetadataReadFailedException extends RuntimeException {

    /**
     * Constructs a new {@code MetadataReadFailedException} with a message indicating the failure
     * to read metadata for a specified file path.
     *
     * @param path the file path associated with the metadata reading failure
     *             used to construct the exception message
     */
    public MetadataReadFailedException(String path) {
        super("Failed to read metadata for: " + path + ".");
    }
}
