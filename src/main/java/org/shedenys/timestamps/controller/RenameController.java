package org.shedenys.timestamps.controller;

import org.shedenys.timestamps.ApplicationProperties;
import org.shedenys.timestamps.exception.MetadataReadFailedException;
import org.shedenys.timestamps.model.directory.usecase.RenameMultipartFileCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;

/**
 * The RenameController class is a REST controller that provides an endpoint
 * for renaming the uploaded file. It leverages the RenameMultipartFileCommand
 * to handle the rename operation and returns the renamed file as an HTTP
 * response.
 */
@RestController
public class RenameController {

    /**
     * Represents the application properties used to configure the behavior of the application.
     */
    private final ApplicationProperties properties;

    /**
     * Constructs a new RenameController with the specified application properties.
     *
     * @param properties the application properties containing configuration settings
     */
    @Autowired
    public RenameController(ApplicationProperties properties) {
        this.properties = properties;
    }

    /**
     * Renames the uploaded file and returns the renamed file as a download response.
     * The endpoint processes a file sent in the request, performs a rename operation,
     * and sends back the renamed file with appropriate headers for file download.
     *
     * @param multipartFile the multipart file uploaded by the client to be renamed
     * @return a ResponseEntity containing the renamed file wrapped in a FileSystemResource,
     * along with HTTP headers for file download
     *
     * @throws ResponseStatusException if the rename operation fails for any reason
     */
    @PostMapping("/rename")
    public ResponseEntity<FileSystemResource> rename(@RequestParam("file") MultipartFile multipartFile) {
        try {
            RenameMultipartFileCommand renameCommand = new RenameMultipartFileCommand(multipartFile, properties);
            renameCommand.execute();
            File file = renameCommand.getFile();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new FileSystemResource(file));
        } catch (MetadataReadFailedException | IOException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }
}
