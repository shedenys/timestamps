package org.shedenys.timestamps.model.file.factory.metadata.creator;

import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import org.shedenys.timestamps.model.file.entity.File;

import java.util.Date;

/**
 * A concrete implementation of the {@link AbstractCreator} class for generating
 * HEIF file instances. This class extracts the original date from the
 * EXIF metadata to construct the associated {@link File} entity.
 * <p>
 * The primary responsibility of this class is to interpret HEIF-specific metadata,
 * specifically the original date, which is utilized as part of the file creation process.
 */
public class HeifFileCreator extends AbstractCreator {

    protected Date getFileDate(Metadata metadata) {
        return getExifDirectory(metadata).getDateOriginal();
    }

    private ExifSubIFDDirectory getExifDirectory(Metadata metadata) {
        return metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
    }
}
