package com.example.wanted.user;

import com.example.wanted.user.adapter.in.web.dto.SignUpRequest;
import com.example.wanted.user.adapter.out.persistence.UserEntity;
import com.example.wanted.user.adapter.out.persistence.UserRepository;
import com.example.wanted.user.application.port.service.AuthService;
import com.example.wanted.user.domain.User;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    @Order(1)
    void 회원가입_테스트() throws Exception {
        mockMvc.perform(post("/auth/sign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"1234@gmail.com\",\"password\":\"12345678\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("0000")))
                .andExpect(jsonPath("$.message", is("OK")));
    }

    @Test
    @Order(2)
    void 로그인_테스트() throws Exception {

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"1234@gmail.com\",\"password\":\"12345678\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("0000")))
                .andExpect(jsonPath("$.message", is("OK")));
    }
}
