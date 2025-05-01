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

    public File create(Path path, Metadata metadata) {
        return new File(
                path,
                getFileDate(metadata),
                getFileExtension(metadata)
        );
    }

    protected abstract Date getFileDate(Metadata metadata);

    private String getFileExtension(Metadata metadata) {
        return metadata
                .getFirstDirectoryOfType(FileTypeDirectory.class)
                .getString(FileTypeDirectory.TAG_EXPECTED_FILE_NAME_EXTENSION);
    }
}
