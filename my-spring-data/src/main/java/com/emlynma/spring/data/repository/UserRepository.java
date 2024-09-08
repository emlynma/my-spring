package com.emlynma.spring.data.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.emlynma.spring.data.entity.User;
import com.emlynma.spring.data.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public User selectByUid(Long uid) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(uid), User::getUid, uid);
        return userMapper.selectOne(queryWrapper, false);
    }

}
