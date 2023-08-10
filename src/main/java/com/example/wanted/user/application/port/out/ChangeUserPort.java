package com.example.wanted.user.application.port.out;

import com.example.wanted.user.domain.User;

public interface ChangeUserPort {
    void save(User user);
}
