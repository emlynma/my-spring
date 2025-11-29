package com.emlyn.spring.trade.service.impl;

import com.emlyn.spring.trade.domain.model.UserInfo;
import com.emlyn.spring.trade.service.UserService;
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
