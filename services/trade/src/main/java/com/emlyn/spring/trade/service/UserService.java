package com.emlyn.spring.trade.service;

import com.emlyn.spring.trade.domain.model.UserInfo;

public interface UserService {

    UserInfo queryUserInfo(Long uid);

}
