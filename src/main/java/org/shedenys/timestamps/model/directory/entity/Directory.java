package org.shedenys.timestamps.model.directory.entity;

import lombok.Getter;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * Represents a directory in the file system.
 * This class encapsulates a {@link Path} object that points to a specific directory.
 * It serves as a lightweight wrapper around the Path instance, providing
 * clear context for operations or commands targeting directory resources.
 */
@Getter
public class Directory {

    /**
     * The filesystem path representing the directory.
     */
    private final Path path;

    /**
     * Constructs a new {@code Directory} instance with the specified path.
     *
     * @param path the {@link Path} representing the directory in the file system.
     *             This path is used to identify and perform operations on the
     *             corresponding directory.
     */
    public Directory(Path path) throws NoSuchFileException {
        if (!exists(path)) {
            throw new NoSuchFileException("Directory does not exist: " + path.toString());
        }
        this.path = path;
    }

    /**
     * Checks whether the specified {@link Path} exists in the file system.
     *
     * @param path the {@link Path} to check for existence.
     * @return {@code true} if the path exists, {@code false} otherwise.
     */
    private static boolean exists(Path path) {
        return Files.exists(path);
    }
}
