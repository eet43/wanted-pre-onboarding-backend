package com.example.wanted.user.adapter.out.persistence;

import com.example.wanted.user.application.port.out.ChangeUserPort;
import com.example.wanted.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements ChangeUserPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        userRepository.save(entity);
    }
}
