package org.shedenys.timestamps;

import org.shedenys.timestamps.model.directory.entity.Directory;
import org.shedenys.timestamps.model.directory.usecase.RenameFilesCommand;

import java.nio.file.*;

/**
 * Main class for the application that handles file renaming based on metadata.
 * This class provides functionality to determine the input directory and
 * execute the renaming operation on the files within the specified directory.
 */
public class Main {

    public static void main(String[] args) {

        Path inputDir;
        if (args.length == 0) {
            inputDir = getDefaultInputDir();
        } else {
            inputDir = Paths.get(args[0]);
        }

        (new RenameFilesCommand(new Directory(inputDir))).execute();
    }

    private static Path getDefaultInputDir() {
        Path currentDir = Paths.get(System.getProperty("user.dir"));

        return currentDir.resolve("input");
    }
}
