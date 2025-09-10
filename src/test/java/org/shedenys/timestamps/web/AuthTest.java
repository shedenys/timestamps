package org.shedenys.timestamps.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the auth security using RenameController class.
 */
@SpringBootTest(
        properties = "application.security.token=y4Gk9rTzQp7Lm2VbN8sC0xHfW1aJe5uR6dBqZc"
)
@AutoConfigureMockMvc
public class AuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void renameWithoutToken_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(
                        post("/rename")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void renameWithInvalidToken_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(
                        post("/rename")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer wrongToken")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void renameWithValidToken_ShouldReturnOk() throws Exception {
        mockMvc.perform(
                        post("/rename")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer y4Gk9rTzQp7Lm2VbN8sC0xHfW1aJe5uR6dBqZc")
                                .content("{\"oldName\":null}")
                )
                .andExpect(status().is(not(401)));
    }
}
