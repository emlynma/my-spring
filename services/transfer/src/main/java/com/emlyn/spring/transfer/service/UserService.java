package com.emlyn.spring.transfer.service;

import com.emlyn.spring.transfer.domain.model.UserInfo;

public interface UserService {

    UserInfo queryUserInfo(Long uid);

}
