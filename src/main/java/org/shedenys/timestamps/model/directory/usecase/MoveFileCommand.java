package org.shedenys.timestamps.model.directory.usecase;

import org.shedenys.timestamps.CommandInterface;
import org.shedenys.timestamps.Config;
import org.shedenys.timestamps.model.file.entity.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * This class represents a command for moving a file to a new location based on its metadata.
 */
public class MoveFileCommand implements CommandInterface {

    /**
     * The file to be moved.
     */
    private final File file;

    /**
     * Constructs a new MoveFileCommand with the specified file.
     *
     * @param file the file to be moved
     */
    public MoveFileCommand(File file) {
        this.file = file;
    }

    /**
     * Executes the move file command.
     */
    @Override
    public void execute() {
        this.moveFile(file);
    }

    /**
     * Moves the specified file to a new location based on its metadata.
     *
     * @param file the file to be moved
     */
    private void moveFile(File file) {
        var geoDirName = file.createGeoLocationBasedDirName();
        var dateDirName = file.createTimestampBasedDirName();

        System.out.println("geo: " + geoDirName);
        System.out.println("date: " + dateDirName);

        var baseDir = file.path().getParent();
        var geoDir = baseDir.resolve(geoDirName);
        var dateDir = geoDir.resolve(dateDirName);

        try {
            if (!Files.exists(geoDir)) {
                Files.createDirectory(geoDir);
            }
            if (!Files.exists(dateDir)) {
                Files.createDirectory(dateDir);
            }

            Path renamedPath = file.createTimestampBasedFilePath();
            Files.move(renamedPath, dateDir.resolve(renamedPath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Skipped. Failed to move the file: " + file.path());
            if (Config.isDevelopment()) {
                e.printStackTrace();
            }
        }
    }
}
