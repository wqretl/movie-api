package com.example.movieapi.integration.controller;

import com.example.movieapi.dto.RegisterRequest;
import com.example.movieapi.utils.BaseIntegrationTest;
import com.example.movieapi.utils.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest extends BaseIntegrationTest {

    @Test
    void register_ValidRequest_ShouldReturn201Created() throws Exception {
        // GIVEN
        RegisterRequest request = TestDataFactory.createValidRegisterRequest();

        // WHEN & THEN
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())   // <-- выводит запрос и ответ
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    @Test
    void register_InvalidRequest_ShouldReturn400BadRequest() throws Exception {
        // GIVEN
        RegisterRequest request = new RegisterRequest();
        request.setFullName("");
        request.setEmail("invalid-email");

        // WHEN & THEN
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())   // <-- выводит запрос и ответ
                .andExpect(status().isBadRequest());
    }
}