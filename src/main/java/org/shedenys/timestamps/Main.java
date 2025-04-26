package org.shedenys.timestamps;

import org.shedenys.timestamps.model.directory.entity.Directory;
import org.shedenys.timestamps.model.directory.usecase.RenameFilesCommand;

import java.nio.file.*;

public class Main {

    public static void main(String[] args) {
        Path currentDir = Paths.get(System.getProperty("user.dir"));
        Path inputDir = currentDir.resolve("input");

        (new RenameFilesCommand(new Directory(inputDir))).execute();
    }
}
