package org.shedenys.timestamps.model.directory.entity;

import java.nio.file.Path;

/**
 * Represents a directory in the file system.
 * This class encapsulates a {@link Path} object that points to a specific directory.
 * It serves as a lightweight wrapper around the Path instance, providing
 * clear context for operations or commands targeting directory resources.
 */
public class Directory {

    private final Path path;

    public Directory(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
