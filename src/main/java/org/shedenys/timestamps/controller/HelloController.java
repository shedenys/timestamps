package org.shedenys.timestamps.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A REST controller that defines a simple endpoint to demonstrate a Hello World
 * example using Spring Boot 3 framework.
 * <p>
 * Annotations:
 * - {@code @RestController}: Indicates that this class will handle HTTP requests
 * and its return values will be written directly to the HTTP response body.
 * - {@code @GetMapping}: Maps HTTP GET requests to the specified path.
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot 3!";
    }
}
