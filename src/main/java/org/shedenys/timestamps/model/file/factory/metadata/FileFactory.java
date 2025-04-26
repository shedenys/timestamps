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

public class FileFactory implements FileFactoryInterface {

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
