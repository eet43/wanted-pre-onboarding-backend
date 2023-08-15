package com.example.wanted.board.adapter.in.web;

import com.example.wanted.board.adapter.out.persistence.BoardEntity;
import com.example.wanted.board.adapter.out.persistence.BoardRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class BoardIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

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

    @Test
    void 게시글_조회() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"123456@gmail.com\",\"password\":\"123456789\"}"))
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        JSONObject json = new JSONObject(responseBody);
        JSONObject result = json.getJSONObject("result");
        String accessToken = result.getString("accessToken");

        List<BoardEntity> entityList = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> BoardEntity.builder().title(("test" + i)).build()).toList();
        boardRepository.saveAll(entityList);

        mockMvc.perform(get("/board/all")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                        .param("size", "5"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.code", is("0000")))
                        .andExpect(jsonPath("$.message", is("OK")))
                        .andExpect(jsonPath("$.result", hasSize(5)))
                        .andExpect(jsonPath("$.result[0].title", is("test6")))
                        .andExpect(jsonPath("$.result[4].title", is("test10")));
    }

    @Test
    void 게시글_수정_조회_삭제_테스트() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"123456@gmail.com\",\"password\":\"123456789\"}"))
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        JSONObject json = new JSONObject(responseBody);
        JSONObject result = json.getJSONObject("result");
        String accessToken = result.getString("accessToken");

        //게시글 생성
        MockHttpServletResponse createBoardResponse = mockMvc.perform(post("/board")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"test\",\"content\":\"example post\"}"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.code", is("0000")))
                        .andExpect(jsonPath("$.message", is("OK")))
                        .andReturn().getResponse();
        String createBoardResponseBody = createBoardResponse.getContentAsString();
        JSONObject boardJson = new JSONObject(createBoardResponseBody);
        Long boardId = boardJson.getJSONObject("result").getLong("boardId");

        //게시글 수정
        mockMvc.perform(put("/board/{boardId}", boardId)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"change\",\"content\":\"change post\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("0000")))
                .andExpect(jsonPath("$.message", is("OK")));

        //게시글 조회
        mockMvc.perform(get("/board/{boardId}", boardId)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("0000")))
                .andExpect(jsonPath("$.message", is("OK")))
                .andExpect(jsonPath("$.result.title", is("change")));

        //게시글 삭제
        mockMvc.perform(delete("/board/{boardId}", boardId)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("0000")))
                .andExpect(jsonPath("$.message", is("OK")));
    }
}