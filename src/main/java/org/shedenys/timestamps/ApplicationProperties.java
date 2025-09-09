package org.shedenys.timestamps;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties class for the application.
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    /**
     * Flag indicating whether the application is running in development mode.
     */
    private boolean development;

    /**
     * Security properties.
     */
    private SecurityProperties security = new SecurityProperties();

    /**
     * Configuration properties for application security settings.
     */
    @Getter
    @Setter
    public static class SecurityProperties {
        private String token;
    }
}
