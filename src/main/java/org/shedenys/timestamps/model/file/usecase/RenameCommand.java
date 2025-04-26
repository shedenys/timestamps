package org.shedenys.timestamps.model.file.usecase;

import org.shedenys.timestamps.CommandInterface;
import org.shedenys.timestamps.model.file.entity.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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
            System.out.println("Renamed: " + path.getFileName() + " -> " + newPath.getFileName());
        } catch (Exception e) {
            System.err.println("Failed to rename " + path.getFileName() + ": " + e.getMessage());
        }
    }
}
