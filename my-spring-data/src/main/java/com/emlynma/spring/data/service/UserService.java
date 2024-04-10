package com.emlynma.spring.data.service;

import com.emlynma.spring.data.entity.User;
import com.emlynma.spring.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByUid(Long uid) {
        Assert.notNull(uid, "id must not be null");
        return userRepository.findByUid(uid);
    }

}
