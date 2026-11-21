package com.emlyn.spring.transfer.service.impl;

import com.emlyn.spring.transfer.domain.model.UserInfo;
import com.emlyn.spring.transfer.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserInfo queryUserInfo(Long uid) {
        UserInfo user = new UserInfo();
        user.setUid(uid);
        user.setUsername("User" + uid);
        return user;
    }

}
