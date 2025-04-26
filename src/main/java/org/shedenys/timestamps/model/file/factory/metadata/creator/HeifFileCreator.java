package org.shedenys.timestamps.model.file.factory.metadata.creator;

import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.util.Date;

public class HeifFileCreator extends AbstractCreator {

    protected Date getFileDate(Metadata metadata) {
        return getExifDirectory(metadata).getDateOriginal();
    }

    private ExifSubIFDDirectory getExifDirectory(Metadata metadata) {
        return metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
    }
}
