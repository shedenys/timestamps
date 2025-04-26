package org.shedenys.timestamps.model.file.factory.metadata.creator;

import com.drew.metadata.Metadata;
import com.drew.metadata.file.FileTypeDirectory;
import org.shedenys.timestamps.model.file.entity.File;

import java.nio.file.Path;
import java.util.Date;

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
