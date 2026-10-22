package org.shedenys.timestamps.model.file.factory.metadata.creator;

import com.drew.metadata.Metadata;
import org.shedenys.timestamps.Config;

import java.util.Date;

/**
 * A file creator for JPEG files.
 */
public class JpegFileCreator extends HeifFileCreator {
    /**
     * {@inheritDoc}
     */
    protected Date getFileDate(Metadata metadata) {
        var directory = getExifDirectory(metadata);
        var timezone = getTimeZone(directory);
        if (timezone == null) {
            timezone = Config.getDefaultJpegTimeZone();
        }

        return directory.getDateOriginal(timezone);
    }
}
