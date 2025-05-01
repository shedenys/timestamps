package org.shedenys.timestamps.model.file.factory.metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.file.FileTypeDirectory;
import org.shedenys.timestamps.model.file.entity.File;
import org.shedenys.timestamps.model.file.factory.FileFactoryInterface;
import org.shedenys.timestamps.model.file.factory.metadata.creator.AbstractCreator;
import org.shedenys.timestamps.model.file.factory.metadata.creator.HeifFileCreator;
import org.shedenys.timestamps.model.file.factory.metadata.creator.QuickTimeFileCreator;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Supplier;

import static org.shedenys.timestamps.model.file.entity.File.MIME_TYPE_IMAGE_HEIF;
import static org.shedenys.timestamps.model.file.entity.File.MIME_TYPE_VIDEO_QUICKTIME;

/**
 * Factory class responsible for creating {@link File} instances.
 * <p>
 * This class implements the {@link FileFactoryInterface} and provides
 * functionality to create file instances based on metadata extracted
 * from an input stream. The creation process supports specific file
 * types, such as QuickTime and HEIF files, through the use of
 * dedicated creators.
 * <p>
 * The factory determines the appropriate creator to use based on the
 * MIME type present in the provided metadata. If the MIME type is not
 * supported, an exception is thrown.
 */
public class FileFactory implements FileFactoryInterface {

    /**
     * The map of mimetypes to their file instance creators.
     */
    private static final Map<String, Supplier<AbstractCreator>> creators = Map.of(
            MIME_TYPE_VIDEO_QUICKTIME, QuickTimeFileCreator::new,
            MIME_TYPE_IMAGE_HEIF, HeifFileCreator::new
    );

    public File create(Path path, InputStream inputStream) throws Exception {
        Metadata metadata = ImageMetadataReader.readMetadata(inputStream);

        return createFromMetadata(path, metadata);
    }

    private File createFromMetadata(Path path, Metadata metadata) throws Exception {
        Supplier<AbstractCreator> creatorSupplier = creators.get(getMimeType(metadata));
        if (null == creatorSupplier) {
            throw new Exception("Unsupported file type.");
        }
        AbstractCreator creator = creatorSupplier.get();

        return creator.create(path, metadata);
    }

    private String getMimeType(Metadata metadata) {
        return metadata
                .getFirstDirectoryOfType(FileTypeDirectory.class)
                .getString(FileTypeDirectory.TAG_DETECTED_FILE_MIME_TYPE);
    }
}
