package org.shedenys.timestamps.web.request;

import lombok.Getter;
import lombok.Setter;
import org.shedenys.timestamps.validation.constraint.NotEmptyFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * Request object for the rename file operation.
 */
@Setter
@Getter
public class RenameRequest {

    /**
     * The file to be renamed.
     */
    @NotEmptyFile
    private MultipartFile file;
}
