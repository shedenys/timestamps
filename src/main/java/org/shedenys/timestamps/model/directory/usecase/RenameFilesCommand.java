package org.shedenys.timestamps.model.directory.usecase;

import lombok.extern.slf4j.Slf4j;
import org.shedenys.timestamps.ApplicationProperties;
import org.shedenys.timestamps.CommandInterface;
import org.shedenys.timestamps.exception.MetadataReadFailedException;
import org.shedenys.timestamps.model.directory.entity.Directory;
import org.shedenys.timestamps.model.file.entity.File;
import org.shedenys.timestamps.model.file.factory.metadata.FileFactory;
import org.shedenys.timestamps.model.file.usecase.RenameCommand;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * A command implementation designed to rename files within a given directory.
 * This class processes files in the specified directory, extracting metadata
 * and renaming them using a timestamp-based naming convention.
 * <p>
 * This class implements the {@link CommandInterface} and adheres to the
 * Command design pattern, encapsulating the rename operation to be executed
 * when called.
 * <p>
 * The renaming operation involves:
 * - Listing all regular files in the specified directory.
 * - Processing each file to extract metadata using a {@link FileFactory}.
 * - Renaming the file using the extracted metadata with the help of a {@link RenameCommand}.
 * <p>
 * Errors during file processing or renaming are logged. If the application is
 * in development mode (determined via {@link ApplicationProperties#isDevelopment()}), additional
 * error details are printed to the console.
 */
@Slf4j
public class RenameFilesCommand implements CommandInterface {

    /**
     * Represents the application properties used to configure the behavior of the application.
     */
    private final ApplicationProperties properties;

    /**
     * The directory that contains the files to be processed by the {@link RenameFilesCommand}.
     * This variable is a reference to an instance of the {@link Directory} class and represents
     * the location within the file system where file renaming operations are performed.
     * It is passed during the initialization of the {@link RenameFilesCommand} and is used
     * to access files for processing and renaming.
     */
    private final Directory dir;

    /**
     * Constructs a new {@code RenameFilesCommand} with the specified directory and properties.
     *
     * @param dir        the directory that contains the files to be renamed
     * @param properties the application properties containing configuration settings
     */
    public RenameFilesCommand(Directory dir, ApplicationProperties properties) {
        this.properties = properties;
        this.dir = dir;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        renameFiles();
    }

    /**
     * Renames files within the specified directory based on extracted metadata.
     * This method processes all regular files in the directory, extracts metadata
     * for each file, and applies a renaming operation.
     */
    private void renameFiles() {
        try (Stream<Path> files = getFileList()) {
            files
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try (InputStream inputStream = Files.newInputStream(path)) {
                            File file = File.of(path, inputStream);
                            (new RenameCommand(file)).execute();
                        } catch (MetadataReadFailedException e) {
                            log.error("Skipped. Failed to read metadata for: {}", path, e);
                            if (properties.isDevelopment()) {
                                log.debug("Stacktrace:", e);
                            }
                        } catch (IOException e) {
                            log.error("Failed to rename {}:", path.getFileName(), e);
                            if (properties.isDevelopment()) {
                                log.debug("Stacktrace:", e);
                            }
                        }
                    });
        } catch (IOException e) {
            log.error("Input/output error. Skipping files in {}...", dir.getPath(), e);
            if (properties.isDevelopment()) {
                log.debug("Stacktrace:", e);
            }
        }
    }

    /**
     * Retrieves a stream of paths representing all files and directories within
     * the directory associated with this command.
     *
     * @return a {@code Stream<Path>} containing the paths of all entries in the directory.
     * The returned stream must be closed after use to release any underlying resources.
     * @throws IOException if an I/O error occurs while accessing the directory.
     */
    private Stream<Path> getFileList() throws IOException {
        return Files.list(dir.getPath());
    }
}
