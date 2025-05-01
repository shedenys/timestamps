package org.shedenys.timestamps.model.file.factory;

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

    File create(Path path, InputStream inputStream) throws Exception;
}
