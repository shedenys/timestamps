package org.shedenys.timestamps.model.file.entity;

import org.shedenys.timestamps.Config;

import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Represents a file with specific attributes such as path, creation date,
 * and file extension. This class provides methods for generating a timestamp-based
 * file name or file path based on the contained attributes.
 * <p>
 * This class is commonly used in combination with metadata extraction and file
 * creation utilities to manage and organize files based on their attributes.
 *
 * @param path        The file system path associated with this file. This path specifies the location of the file
 *                    within the file system and is utilized for operations such as resolving sibling paths
 *                    and generating full paths using attributes of this class.
 * @param date        Represents the creation or associated timestamp of the file.
 *                    This attribute is used for date-specific operations, such as
 *                    generating file names and paths that incorporate a timestamp.
 * @param geoLocation Represents the geographic location of this file as an array of two {@code Double} values
 *                    representing latitude and longitude. It is used to provide location-specific information
 *                    about the file's origin or context.
 * @param extension   Represents the file extension of this file. The extension is used to identify
 *                    the type or format of the file (e.g., "txt", "jpg", "mp4"). It is commonly
 *                    appended to file names or paths to denote the file's type and is utilized
 *                    when generating timestamp-based file names or paths.
 */
public record File(Path path, Date date, Double[] geoLocation, String extension) {

    public static final String MIME_TYPE_VIDEO_QUICKTIME = "video/quicktime";
    public static final String MIME_TYPE_IMAGE_HEIF = "image/heif";
    public static final String MIME_TYPE_IMAGE_JPEG = "image/jpeg";

    /**
     * Defines the date-time pattern used for generating timestamp-based strings in the format
     * "yyyy-MM-dd-HH-mm-ss". This pattern is commonly applied when creating file names or paths
     * that require a specific timestamp format for consistency and organization.
     * <p>
     * The pattern includes:
     * - `yyyy`: 4-digit year
     * - `MM`: 2-digit month
     * - `dd`: 2-digit day of the month
     * - `HH`: 2-digit hour (24-hour format)
     * - `mm`: 2-digit minute
     * - `ss`: 2-digit second
     * <p>
     * This constant is used internally in methods that generate file names or paths incorporating
     * timestamp information based on the provided {@code date} attribute.
     */
    private static final String TIMESTAMP_DATE_PATTERN = "yyyy-MM-dd-HH-mm-ss";

    /**
     * Defines the date-time pattern used for generating timestamp-based strings in the format "yyyy-MM".
     * <p>
     * The pattern includes:
     * - `yyyy`: 4-digit year
     * - `MM`: 2-digit month
     * <p>
     * This constant is used internally in methods that generate file names or paths incorporating
     * timestamp information based on the provided {@code date} attribute.
     */
    private static final String MONTH_YEAR_DATE_PATTERN = "yyyy-MM";

    /**
     * Creates a file name based on the associated timestamp and file extension.
     * The file name is constructed by formatting the date using a predefined
     * timestamp pattern and appending the file extension.
     *
     * @return a {@code String} representing the generated file name in the
     * format "formatted_timestamp.extension"
     */
    public String createTimestampBasedFileName() {
        return createDateFormat(TIMESTAMP_DATE_PATTERN).format(date) + "." + extension;
    }

    /**
     * Creates a directory name based on the associated timestamp.
     * The directory name is formatted using a predefined pattern that includes
     * the year and month of the associated date.
     *
     * @return a {@code String} representing the generated directory name in the
     * format "yyyy-MM"
     */
    public String createTimestampBasedDirName() {
        return createDateFormat(MONTH_YEAR_DATE_PATTERN).format(date);
    }

    /**
     * Creates a directory name based on the associated timestamp.
     * The directory name is formatted using a predefined pattern that includes
     * the year and month of the associated date.
     *
     * @return a {@code String} representing the generated directory name in the
     * format "lon, lat"
     */
    public String createGeoLocationBasedDirName() {
        return String.format("%.2f", geoLocation[0]) + ", " + String.format("%.2f", geoLocation[1]);
    }

    /**
     * Creates a new file path based on the associated timestamp and file extension.
     * The resulting path is derived from the current path by replacing the file name
     * with a timestamp-based file name generated using the associated date and extension.
     *
     * @return a {@code Path} representing the new file path, with the file name replaced
     * by a timestamp-based file name
     */
    public Path createTimestampBasedFilePath() {
        return path.resolveSibling(createTimestampBasedFileName());
    }

    /**
     * Creates a date format object based on the specified pattern.
     *
     * @param pattern the pattern to use for formatting dates
     * @return a {@code DateFormat} object configured with the specified pattern and timezone
     */
    private static DateFormat createDateFormat(String pattern) {
        DateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone(Config.get("timezone")));

        return sdf;
    }
}
