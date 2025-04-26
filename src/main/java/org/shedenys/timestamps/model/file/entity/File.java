package org.shedenys.timestamps.model.file.entity;

import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The file entity.
 */
public class File {

    public static final String MIME_TYPE_VIDEO_QUICKTIME = "video/quicktime";
    public static final String MIME_TYPE_IMAGE_HEIF = "image/heif";

    private static final String TIMESTAMP_DATE_PATTERN = "yyyy-MM-dd-HH-mm-ss";

    private final Path path;

    private final Date date;

    private final String extension;

    public File(Path path, Date date, String extension) {
        this.path = path;
        this.date = date;
        this.extension = extension;
    }

    public Path getPath() {
        return path;
    }

    public Date getDate() {
        return date;
    }

    public String getExtension() {
        return extension;
    }

    public String createTimestampBasedFileName() {
        DateFormat sdf = new SimpleDateFormat(TIMESTAMP_DATE_PATTERN);
        String formatted = sdf.format(date);

        return formatted + "." + extension;
    }

    public Path createTimestampBasedFilePath() {
        return path.resolveSibling(createTimestampBasedFileName());
    }
}
