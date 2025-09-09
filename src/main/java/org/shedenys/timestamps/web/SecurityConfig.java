package org.shedenys.timestamps.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for defining security-related settings in the application.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the HttpSecurity object used to configure the security filter chain
     * @return a configured SecurityFilterChain instance
     * @throws Exception if an error occurs while building the security filter chain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }
}
