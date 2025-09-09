package org.shedenys.timestamps.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.shedenys.timestamps.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * This filter checks the "Authorization" header in incoming HTTP requests.
 * If the token matches the expected token configured in application properties,
 * the request is allowed to proceed. Otherwise, the filter returns a 401
 * Unauthorized response.
 */
@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    /**
     * The expected token configured in application properties.
     */
    private final String expectedToken;

    /**
     * Constructs a new {@code TokenAuthFilter} instance.
     *
     * @param properties the application properties.
     */
    @Autowired
    public TokenAuthFilter(ApplicationProperties properties) {
        expectedToken = properties.getSecurity().getToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        // only allow requests with the correct token
        if (header == null || !header.equals("Bearer " + expectedToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

            return;
        }
        filterChain.doFilter(request, response);
    }
}
