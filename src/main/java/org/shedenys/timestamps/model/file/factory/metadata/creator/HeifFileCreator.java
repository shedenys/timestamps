package org.shedenys.timestamps.model.file.factory.metadata.creator;

import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import org.shedenys.timestamps.Config;
import org.shedenys.timestamps.model.file.entity.File;

import java.util.Date;
import java.util.TimeZone;

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
        var directory = getExifDirectory(metadata);
        var timezone = getTimeZone(directory);
        if (timezone == null) {
            throw new RuntimeException("No timezone found");
        }

        return directory.getDateOriginal(timezone);
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
    protected ExifSubIFDDirectory getExifDirectory(Metadata metadata) {
        return metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
    }

    /**
     * Retrieves the time zone information from the provided EXIF directory.
     *
     * @param directory the EXIF directory containing time zone information
     * @return a {@link TimeZone} instance representing the specified time zone,
     * or {@code null} if the time zone offset is not present or does not match the expected format
     */
    @Nullable
    protected TimeZone getTimeZone(ExifSubIFDDirectory directory)
    {
        String timeOffset = directory.getString(ExifSubIFDDirectory.TAG_TIME_ZONE);
        if (timeOffset != null && timeOffset.matches("[\\+\\-]\\d\\d:\\d\\d")) {
            return TimeZone.getTimeZone("GMT" + timeOffset);
        } else {
            return null;
        }
    }
}
