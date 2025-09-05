package org.shedenys.timestamps.model.file.factory;

import org.shedenys.timestamps.exception.MetadataReadFailedException;
import org.shedenys.timestamps.model.file.entity.File;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * Interface defining a factory for creating {@link File} instances.
 * <p>
 * Implementations of this interface are responsible for creating file objects
 * based on a given file path and metadata from an input stream. This allows
 * the creation process to be customized based on file-specific attributes and
 * metadata.
 */
public interface FileFactoryInterface {

    /**
     * Creates a {@link File} instance based on the specified file path and metadata
     * extracted from the provided input stream.
     *
     * @param path        the file path for which the {@link File} instance is to be created
     * @param inputStream the input stream containing metadata used in creating the {@link File} instance
     * @return a {@link File} instance created using the provided path and metadata
     * @throws MetadataReadFailedException if an error occurs during metadata extraction or file creation
     */
    File create(Path path, InputStream inputStream) throws MetadataReadFailedException;
}
