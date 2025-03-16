package com.emlynma.spring.data.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.emlynma.spring.core.BaseErrorCode;
import com.emlynma.spring.core.BaseException;
import com.emlynma.spring.data.entity.User;
import com.emlynma.spring.data.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public void save(User user) {
        boolean result = userMapper.insertOrUpdate(user);
        if (!result) {
            throw new BaseException(BaseErrorCode.DATABASE_ERROR, "save user failed");
        }
    }

    public User saveAndLoad(User user) {
        save(user);
        return findByUid(user.getUid());
    }

    public void remove(User user) {

    }

    public User findByUid(Long uid) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUid, uid);
        return userMapper.selectOne(queryWrapper);
    }

    public User findByIdentifier(String uname, String phone, String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(uname), User::getUname, uname)
                    .eq(Objects.nonNull(phone), User::getPhone, phone)
                    .eq(Objects.nonNull(email), User::getEmail, email);
        return userMapper.selectOne(queryWrapper);
    }

}
