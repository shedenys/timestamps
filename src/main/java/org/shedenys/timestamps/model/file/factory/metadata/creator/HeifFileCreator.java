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

    /**
     * {@inheritDoc}
     */
    protected Date getFileDate(Metadata metadata) {
        return getExifDirectory(metadata).getDateOriginal();
    }

    /**
     * Retrieves the first {@link ExifSubIFDDirectory} instance from the provided metadata.
     * This directory contains EXIF metadata, such as the original creation date,
     * which can be utilized as needed.
     *
     * @param metadata the metadata containing information extracted from a file,
     *                 which may include EXIF-specific details
     * @return the first instance of {@link ExifSubIFDDirectory} found within the metadata,
     * or {@code null} if no such directory is present
     */
    private ExifSubIFDDirectory getExifDirectory(Metadata metadata) {
        return metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
    }
}
