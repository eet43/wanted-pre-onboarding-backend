package com.example.wanted.board.adapter.in.web;

import com.example.wanted.user.adapter.in.web.dto.SignUpRequest;
import com.example.wanted.user.adapter.out.persistence.UserEntity;
import com.example.wanted.user.adapter.out.persistence.UserRepository;
import com.example.wanted.user.domain.User;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BoardIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        // 회원 정보 생성 코드
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity user = UserEntity.builder()
                .email("123456@gmail.com")
                .password(passwordEncoder.encode("123456789"))
                .build();
        userRepository.save(user);
    }

    @Test
    void 게시글_생성() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"123456@gmail.com\",\"password\":\"123456789\"}"))
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        JSONObject json = new JSONObject(responseBody);
        JSONObject result = json.getJSONObject("result");
        String accessToken = result.getString("accessToken");


        mockMvc.perform(post("/board")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"test\",\"content\":\"example post\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("0000")))
                .andExpect(jsonPath("$.message", is("OK")));
    }
}