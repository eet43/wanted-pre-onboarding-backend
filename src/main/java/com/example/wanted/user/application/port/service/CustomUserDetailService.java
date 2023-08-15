package com.example.wanted.user.application.port.service;

import com.example.wanted.user.adapter.in.web.dto.CustomUserDetails;
import com.example.wanted.user.application.port.out.LoadUserPort;
import com.example.wanted.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final LoadUserPort loadUserPort;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser = loadUserPort.loadUserByUsername(username);
        return new CustomUserDetails(findUser);
    }
}
