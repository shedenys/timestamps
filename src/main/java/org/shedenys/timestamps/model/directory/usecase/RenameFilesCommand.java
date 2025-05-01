package org.shedenys.timestamps.model.directory.usecase;

import org.shedenys.timestamps.CommandInterface;
import org.shedenys.timestamps.Config;
import org.shedenys.timestamps.model.directory.entity.Directory;
import org.shedenys.timestamps.model.file.entity.File;
import org.shedenys.timestamps.model.file.factory.metadata.FileFactory;
import org.shedenys.timestamps.model.file.usecase.RenameCommand;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class RenameFilesCommand implements CommandInterface {

    private final Directory dir;

    public RenameFilesCommand(Directory dir) {
        this.dir = dir;
    }

    @Override
    public void execute() {
        renameFiles();
    }

    private void renameFiles() {
        try (Stream<Path> files = getFileList()) {
            files
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try (InputStream inputStream = Files.newInputStream(path)) {
                            File file = (new FileFactory()).create(path, inputStream);
                            (new RenameCommand(file)).execute();
                        } catch (Exception e) {
                            System.err.println("Skipped. Failed to read metadata for: " + path);
                            if (Config.isDevelopment()) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (IOException e) {
            if (Config.isDevelopment()) {
                e.printStackTrace();
            }
        }
    }

    private Stream<Path> getFileList() throws IOException {
        return Files.list(dir.getPath());
    }
}
