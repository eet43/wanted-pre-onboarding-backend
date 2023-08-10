package com.example.wanted.user.application.port.out;

import com.example.wanted.user.adapter.out.persistence.UserEntity;

public interface ChangeUserPort {
    void save(UserEntity entity);
}
