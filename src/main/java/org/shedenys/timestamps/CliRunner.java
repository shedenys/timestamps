package org.shedenys.timestamps;

import lombok.extern.slf4j.Slf4j;
import org.shedenys.timestamps.model.directory.entity.Directory;
import org.shedenys.timestamps.model.directory.usecase.RenameFilesCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.file.*;

/**
 * Main class for the application that handles file renaming based on metadata.
 * This class provides functionality to determine the input directory and
 * execute the renaming operation on the files within the specified directory.
 */
@Component
@Profile("cli")
@Slf4j
public class CliRunner implements CommandLineRunner {

    /**
     * Represents the application properties used to configure the behavior of the application.
     */
    private final ApplicationProperties properties;

    /**
     * Constructs a new {@code CliRunner} instance.
     *
     * @param properties the application properties used to configure the behavior of the application
     */
    @Autowired
    public CliRunner(ApplicationProperties properties) {
        this.properties = properties;
    }

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
    @Override
    public void run(String... args) {

        Path inputDir;
        if (args.length == 0) {
            System.err.println("Error. Input directory not specified");

            return;
        } else {
            inputDir = Paths.get(args[0]);
        }

        try {
            (new RenameFilesCommand(new Directory(inputDir), properties)).execute();
        } catch (NoSuchFileException e) {
            if (properties.isDevelopment()) {
                log.debug("Stacktrace:", e);
            } else {
                log.error(e.getMessage(), e);
            }
        }
    }
}
