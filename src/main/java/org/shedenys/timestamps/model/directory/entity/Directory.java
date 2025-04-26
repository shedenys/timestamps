package org.shedenys.timestamps.model.directory.entity;

import java.nio.file.Path;

public class Directory {

    private final Path path;

    public Directory(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
