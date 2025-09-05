package org.shedenys.timestamps.model.directory.usecase;

import lombok.Getter;
import org.shedenys.timestamps.CommandInterface;
import org.shedenys.timestamps.Config;
import org.shedenys.timestamps.exception.MetadataReadFailedException;
import org.shedenys.timestamps.model.file.entity.File;
import org.shedenys.timestamps.model.file.factory.metadata.FileFactory;
import org.shedenys.timestamps.model.file.usecase.RenameCommand;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/**
 * The {@code RenameMultipartFileCommand} class implements the {@link CommandInterface}.
 * This class is responsible for handling the renaming of a {@link MultipartFile} by
 * creating a temporary file on the file system, converting it into a format
 * compatible with other components, and executing a rename operation through a
 * {@link RenameCommand}.
 */
@Getter
public class RenameMultipartFileCommand implements CommandInterface {

    /**
     * Represents a temporary file created on the file system for handling a {@link MultipartFile}.
     */
    private java.io.File file;

    /**
     * Constructs a new {@code RenameMultipartFileCommand} with the given multipart file.
     *
     * @param multipartFile the multipart file to be processed.
     */
    public RenameMultipartFileCommand(MultipartFile multipartFile) throws IOException {
        try {
            file = saveMultipartFileAsTemporary(multipartFile);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            if (Config.isDevelopment()) {
                e.printStackTrace();
            }
            throw e;
        }
    }

    /**
     * Executes the renaming operation encapsulated within this command object.
     */
    @Override
    public void execute() throws MetadataReadFailedException, IOException {
        if (null == file) {
            return;
        }
        try {
            RenameCommand command = (new RenameCommand(fileToEntity(file)));
            command.execute();
            file = command.getFile().toIOFile();
        } catch (MetadataReadFailedException | IOException e) {
            System.err.println(e.getMessage());
            if (Config.isDevelopment()) {
                e.printStackTrace();
            }
            throw e;
        }
    }

    /**
     * Saves a {@link MultipartFile} as a temporary file in the system's temporary directory.
     *
     * @param file the {@code MultipartFile} to be saved as a temporary file. The file must not be null,
     *             and the original filename must be non-null.
     * @return a {@link java.io.File} object representing the created temporary file.
     * @throws IOException if an I/O error occurs during the file transfer process.
     */
    private java.io.File saveMultipartFileAsTemporary(MultipartFile file) throws IOException {
        java.io.File tempFile = new java.io.File(
                System.getProperty("java.io.tmpdir"),
                Objects.requireNonNull(file.getOriginalFilename())
        );
        file.transferTo(tempFile);

        return tempFile;
    }

    /**
     * Converts a provided {@link java.io.File} into an instance of {@link File}.
     *
     * @param file the {@link java.io.File} to be converted into a {@link File}.
     *             This file represents the source for metadata extraction.
     * @return a {@link File} instance created using the provided file's path
     * and input stream.
     * @throws MetadataReadFailedException if an error occurs during the conversion process,
     *                                     such as unsupported file types or I/O issues.
     * @throws FileNotFoundException       if the specified file does not exist.
     */
    private File fileToEntity(java.io.File file) throws MetadataReadFailedException, FileNotFoundException {
        return File.of(file.toPath(), new FileInputStream(file));
    }
}
