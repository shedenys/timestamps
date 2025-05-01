package org.shedenys.timestamps.model.file.usecase;

import org.shedenys.timestamps.CommandInterface;
import org.shedenys.timestamps.model.file.entity.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * A command implementation for renaming a file. This class defines the
 * behavior for renaming a file based on a timestamp-based naming convention.
 * It uses the {@link File} entity to determine the current path and the
 * new renamed path.
 * <p>
 * This class adheres to the Command design pattern and implements the
 * {@link CommandInterface}, which encapsulates the rename operation
 * to be executed when called.
 */
public class RenameCommand implements CommandInterface {

    private final File file;

    public RenameCommand(File file) {
        this.file = file;
    }

    @Override
    public void execute() {
        rename(file.getPath(), file.createTimestampBasedFilePath());
    }

    private void rename(Path path, Path newPath) {
        try {
            Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Renamed. " + path.getFileName() + " -> " + newPath.getFileName());
        } catch (Exception e) {
            System.err.println("Failed to rename " + path.getFileName() + ": " + e.getMessage());
        }
    }
}
