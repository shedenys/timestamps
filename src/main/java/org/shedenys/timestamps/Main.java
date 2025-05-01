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

    /**
     * The main entry point of the application.
     * This method determines the input directory for the files to be processed
     * and initiates the renaming operation by executing a {@link RenameFilesCommand}.
     *
     * @param args the command-line arguments where the first argument specifies
     *             the path to the input directory. If no arguments are provided,
     *             a default directory named "input" relative to the current working
     *             directory is used.
     */
    public static void main(String[] args) {

        Path inputDir;
        if (args.length == 0) {
            System.err.println("Error. Input directory not specified");

            return;
        } else {
            inputDir = Paths.get(args[0]);
        }

        try {
            (new RenameFilesCommand(new Directory(inputDir))).execute();
        } catch (NoSuchFileException e) {
            if (Config.isDevelopment()) {
                e.printStackTrace();
            } else {
                System.err.println(e.getMessage());
            }
        }
    }
}
