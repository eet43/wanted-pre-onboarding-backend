package com.example.wanted.user.adapter.out.persistence;

import com.example.wanted.common.response.CodeSet;
import com.example.wanted.common.response.CustomException;
import com.example.wanted.common.response.FilterException;
import com.example.wanted.user.application.port.out.ChangeUserPort;
import com.example.wanted.user.application.port.out.LoadUserPort;
import com.example.wanted.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements LoadUserPort, ChangeUserPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        userRepository.save(entity);
    }

    @Override
    public User findUser(String email) {
        UserEntity findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CodeSet.INVALID_EMAIL));

        return userMapper.toDomain(findUser);
    }

    @Override
    public void checkEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new CustomException(CodeSet.ALREADY_EMAIL);
        }
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity findUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new FilterException(CodeSet.INVALID_EMAIL));
        return userMapper.toDomain(findUser);
    }
}
