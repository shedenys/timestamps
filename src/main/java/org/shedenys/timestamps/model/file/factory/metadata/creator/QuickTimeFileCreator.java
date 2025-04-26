package org.shedenys.timestamps.model.file.factory.metadata.creator;

import com.drew.metadata.Metadata;
import com.drew.metadata.mov.QuickTimeDirectory;

import java.util.Date;

public class QuickTimeFileCreator extends AbstractCreator {

    protected Date getFileDate(Metadata metadata) {
        return getQuickTimeDirectory(metadata).getDate(QuickTimeDirectory.TAG_CREATION_TIME);
    }

    private QuickTimeDirectory getQuickTimeDirectory(Metadata metadata) {
        return metadata.getFirstDirectoryOfType(QuickTimeDirectory.class);
    }
}
