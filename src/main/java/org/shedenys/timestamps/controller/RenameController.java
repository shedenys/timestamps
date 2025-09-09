package org.shedenys.timestamps.controller;

import jakarta.validation.Valid;
import org.shedenys.timestamps.ApplicationProperties;
import org.shedenys.timestamps.request.RenameRequest;
import org.shedenys.timestamps.exception.MetadataReadFailedException;
import org.shedenys.timestamps.model.directory.usecase.RenameMultipartFileCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
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
     * Processes a file rename request and returns the renamed file as a download response.
     *
     * @param request the RenameRequest containing the multipart file and any additional
     *                rename parameters
     * @return a ResponseEntity containing the renamed file as a FileSystemResource with
     * content disposition headers for file download
     * @throws ResponseStatusException with HTTP 422 (Unprocessable Entity) if metadata
     *                                 reading fails or if there are IO issues during the rename operation
     */
    @PostMapping("/rename")
    public ResponseEntity<FileSystemResource> rename(@Valid @ModelAttribute RenameRequest request) {
        try {
            RenameMultipartFileCommand renameCommand = new RenameMultipartFileCommand(request.getFile(), properties);
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
