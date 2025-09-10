package org.shedenys.timestamps.web.request;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * The {@code ErrorAttributes} class is responsible for extracting error attributes.
 */
@Component
public class ErrorAttributes extends DefaultErrorAttributes {

    /**
     * Retrieves error attributes from the specified request.
     *
     * @param webRequest the source request
     * @param options    options for error attribute contents
     */
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest,
                                                  ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

        Throwable error = getError(webRequest);

        if (error instanceof org.springframework.web.bind.MethodArgumentNotValidException ex) {
            // Take the first validation error message
            String message = ex.getBindingResult().getFieldErrors().stream()
                    .findFirst()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .orElse("Validation error");

            errorAttributes.put("error", message);
        }

        return errorAttributes;
    }
}
