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

    /**
     * Represents the file associated with the rename command operation. This file serves as
     * the primary target for the rename process, providing access to details such as its current
     * path and the ability to generate a new timestamp-based path.
     */
    private final File file;

    /**
     * Constructs a new {@code RenameCommand} with the specified file. This command
     * encapsulates the behavior for renaming the specified file based on a
     * timestamp-based naming convention.
     *
     * @param file the file to be renamed.
     */
    public RenameCommand(File file) {
        this.file = file;
    }

    /**
     * Executes the rename operation encapsulated by this command.
     */
    @Override
    public void execute() {
        rename(file.path(), file.createTimestampBasedFilePath());
    }

    /**
     * Renames a file or directory from the specified source path to the specified target path.
     * If a file with the target name already exists, it will be replaced.
     * Logs the success or failure of the rename operation.
     *
     * @param path    the current path of the file or directory to be renamed
     * @param newPath the new path of the file or directory after the rename operation
     */
    private void rename(Path path, Path newPath) {
        try {
            Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Renamed. " + path.getFileName() + " -> " + newPath.getFileName());
        } catch (Exception e) {
            System.err.println("Failed to rename " + path.getFileName() + ": " + e.getMessage());
        }
    }
}
