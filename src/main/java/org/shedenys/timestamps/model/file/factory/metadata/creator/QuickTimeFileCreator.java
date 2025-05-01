package org.shedenys.timestamps.model.file.factory.metadata.creator;

import com.drew.metadata.Metadata;
import com.drew.metadata.mov.QuickTimeDirectory;
import org.shedenys.timestamps.model.file.entity.File;

import java.util.Date;

/**
 * A concrete implementation of the {@link AbstractCreator} class for generating
 * QuickTime file instances. This class extracts the creation date from the
 * QuickTime-specific metadata to be used in constructing the associated {@link File}
 * entity.
 * <p>
 * The primary responsibility of this class is to interpret QuickTime metadata,
 * specifically the creation time, which is utilized as part of the file creation process.
 */
public class QuickTimeFileCreator extends AbstractCreator {

    protected Date getFileDate(Metadata metadata) {
        return getQuickTimeDirectory(metadata).getDate(QuickTimeDirectory.TAG_CREATION_TIME);
    }

    private QuickTimeDirectory getQuickTimeDirectory(Metadata metadata) {
        return metadata.getFirstDirectoryOfType(QuickTimeDirectory.class);
    }
}
