package com.example.wanted.user.adapter.out.persistence;

import com.example.wanted.security.JwtProvider;
import com.example.wanted.user.application.port.out.ChangeUserPort;
import com.example.wanted.user.application.port.out.LoadUserPort;
import com.example.wanted.user.application.port.service.AuthService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({UserMapper.class, UserRepository.class, UserPersistenceAdapter.class})
class UserPersistenceAdapterTest {
    @Autowired
    UserPersistenceAdapter userPersistenceAdapter;

    @MockBean
    UserMapper userMapper;

    @MockBean
    UserRepository userRepository;

//    @Test
//    void () throws Exception {
//        //given
//
//        //when
//
//        //then
//    }
}