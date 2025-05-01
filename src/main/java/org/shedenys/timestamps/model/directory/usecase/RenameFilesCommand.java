package org.shedenys.timestamps.model.directory.usecase;

import org.shedenys.timestamps.CommandInterface;
import org.shedenys.timestamps.Config;
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
 * in development mode (determined via {@link Config#isDevelopment()}), additional
 * error details are printed to the console.
 */
public class RenameFilesCommand implements CommandInterface {

    private final Directory dir;

    public RenameFilesCommand(Directory dir) {
        this.dir = dir;
    }

    @Override
    public void execute() {
        renameFiles();
    }

    private void renameFiles() {
        try (Stream<Path> files = getFileList()) {
            files
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try (InputStream inputStream = Files.newInputStream(path)) {
                            File file = (new FileFactory()).create(path, inputStream);
                            (new RenameCommand(file)).execute();
                        } catch (Exception e) {
                            System.err.println("Skipped. Failed to read metadata for: " + path);
                            if (Config.isDevelopment()) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (IOException e) {
            if (Config.isDevelopment()) {
                e.printStackTrace();
            }
        }
    }

    private Stream<Path> getFileList() throws IOException {
        return Files.list(dir.getPath());
    }
}
