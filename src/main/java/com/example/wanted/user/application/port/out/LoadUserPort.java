package com.example.wanted.user.application.port.out;

import com.example.wanted.user.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoadUserPort {
    User findUser(String email);
    void checkEmail(String email);
    User loadUserByUsername(String username);
}
