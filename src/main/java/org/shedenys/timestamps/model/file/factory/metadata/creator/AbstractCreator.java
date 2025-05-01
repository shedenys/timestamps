package org.shedenys.timestamps.model.file.factory.metadata.creator;

import com.drew.metadata.Metadata;
import com.drew.metadata.file.FileTypeDirectory;
import org.shedenys.timestamps.model.file.entity.File;

import java.nio.file.Path;
import java.util.Date;

/**
 * An abstract class that serves as a blueprint for creating instances of the {@link File} class.
 * Subclasses are responsible for implementing the logic to extract the metadata necessary for
 * creating a file, such as the file's creation date.
 * <p>
 * This class provides a template method pattern, where the {@link #create(Path, Metadata)} method
 * performs the file creation process by combining the provided {@link Path}, a date derived from
 * the metadata (using the abstract {@link #getFileDate(Metadata)} method), and the file extension
 * extracted internally.
 */
public abstract class AbstractCreator {

    /**
     * Creates a {@link File} instance using the provided {@link Path} and {@link Metadata}.
     * This method combines the specified file path, a date retrieved from the metadata,
     * and the file extension extracted from the metadata to construct the {@link File}.
     *
     * @param path     the file path to be associated with the {@link File} instance
     * @param metadata the metadata containing information such as the file's creation date
     *                 and file extension
     * @return a {@link File} instance created using the provided path and metadata
     */
    public File create(Path path, Metadata metadata) {
        return new File(
                path,
                getFileDate(metadata),
                getFileExtension(metadata)
        );
    }

    /**
     * Retrieves the file creation date from the provided metadata.
     * This method must be implemented by subclasses to extract the
     * appropriate date information specific to their metadata format.
     *
     * @param metadata the metadata from which the creation date is to be extracted
     * @return the extracted creation date as a {@code Date} object, or {@code null} if no valid date is found
     */
    protected abstract Date getFileDate(Metadata metadata);

    /**
     * Extracts the file extension from the specified metadata. The extension is determined
     * based on the file type directory present in the metadata.
     *
     * @param metadata the metadata from which the file extension is to be extracted
     * @return the extracted file extension as a {@code String}, or {@code null} if no valid
     * file type directory or extension is found
     */
    private String getFileExtension(Metadata metadata) {
        return metadata
                .getFirstDirectoryOfType(FileTypeDirectory.class)
                .getString(FileTypeDirectory.TAG_EXPECTED_FILE_NAME_EXTENSION);
    }
}
