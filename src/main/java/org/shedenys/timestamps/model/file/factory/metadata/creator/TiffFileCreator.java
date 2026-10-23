package org.shedenys.timestamps.model.file.factory.metadata.creator;

import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

public class TiffFileCreator extends HeifFileCreator {
    @Override
    protected Date getFileDate(Metadata metadata) {
        @Nullable TimeZone timezone = null;
        for (var directory : getExifSubIFDDirectories(metadata)) {
            timezone = getTimeZone(directory);

            if (timezone != null) {
                break;
            }
        }
        if (timezone == null) {
            throw new RuntimeException("No timezone found");
        }

        return getExifIFD0DirectoryDirectory(metadata).getDate(ExifIFD0Directory.TAG_DATETIME, timezone);
    }

    /**
     * Retrieves the first {@link ExifIFD0Directory} instance from the provided metadata.
     * This directory contains EXIF metadata, such as the original creation date,
     * which can be utilized as needed.
     *
     * @param metadata the metadata containing information extracted from a file,
     *                 which may include EXIF-specific details
     * @return the first instance of {@link ExifIFD0Directory} found within the metadata,
     * or {@code null} if no such directory is present
     */
    private ExifIFD0Directory getExifIFD0DirectoryDirectory(Metadata metadata) {
        return metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
    }

    /**
     * Retrieves a collection of {@link ExifSubIFDDirectory} instances from the provided metadata.
     * These directories contain EXIF metadata, such as the original creation date,
     * which can be utilized as needed.
     *
     * @param metadata the metadata containing information extracted from a file,
     *                 which may include EXIF-specific details
     * @return a collection of {@link ExifSubIFDDirectory} instances found within the metadata,
     * or an empty collection if no such directories are present
     */
    private Collection<ExifSubIFDDirectory> getExifSubIFDDirectories(Metadata metadata) {
        return metadata.getDirectoriesOfType(ExifSubIFDDirectory.class);
    }
}
