package org.shedenys.timestamps.model.directory.entity;

import java.nio.file.Path;

/**
 * Represents a directory in the file system.
 * This class encapsulates a {@link Path} object that points to a specific directory.
 * It serves as a lightweight wrapper around the Path instance, providing
 * clear context for operations or commands targeting directory resources.
 */
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
    public Directory(Path path) {
        this.path = path;
    }

    /**
     * Returns the filesystem path associated with this object.
     *
     * @return the {@link Path} representing the directory in the file system.
     */
    public Path getPath() {
        return path;
    }
}
