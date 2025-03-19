package com.emlynma.spring.user.handler;

import com.emlynma.spring.core.BaseErrorCode;
import com.emlynma.spring.core.BaseException;
import com.emlynma.spring.core.annotation.Handler;
import com.emlynma.spring.data.entity.Auth;
import com.emlynma.spring.data.entity.User;
import com.emlynma.spring.data.entity.enums.AuthTypeEnum;
import com.emlynma.spring.data.entity.enums.UserStatusEnum;
import com.emlynma.spring.data.repository.AuthRepository;
import com.emlynma.spring.data.repository.UserRepository;
import com.emlynma.spring.user.contract.request.RegisterRequest;
import com.emlynma.spring.user.contract.response.RegisterResponse;
import com.emlynma.spring.user.handler.context.ContextHolder;
import com.emlynma.spring.user.service.IdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Handler
@RequiredArgsConstructor
public class RegisterHandler {

    private final ContextHolder contextHolder;
    private final IdService idService;
    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    public RegisterResponse handle(RegisterRequest request) {

        this.checkRequest(request);

        this.checkAvailability(request);

        this.createUser(request);

        return this.buildResponse();
    }

    private void checkRequest(RegisterRequest request) {
        if (ObjectUtils.isEmpty(request.getUname())
                && ObjectUtils.isEmpty(request.getPhone())
                && ObjectUtils.isEmpty(request.getEmail())) {
            throw new BaseException(BaseErrorCode.PARAM_ERROR, "no valid identifier");
        }
        if (StringUtils.hasText(request.getPassword())) {
            if (request.getPassword().length() < 6) {
                throw new BaseException(BaseErrorCode.PARAM_ERROR, "password too short");
            }
        }
    }

    private void checkAvailability(RegisterRequest request) {
        if (StringUtils.hasText(request.getUname())) {
            User user = userRepository.findByUname(request.getUname());
            if (user != null) {
                throw new BaseException(BaseErrorCode.PARAM_ERROR, "uname already exist");
            }
        }
        if (StringUtils.hasText(request.getPhone())) {
            User user = userRepository.findByPhone(request.getPhone());
            if (user != null) {
                throw new BaseException(BaseErrorCode.PARAM_ERROR, "phone already exist");
            }
        }
        if (StringUtils.hasText(request.getEmail())) {
            User user = userRepository.findByEmail(request.getEmail());
            if (user != null) {
                throw new BaseException(BaseErrorCode.PARAM_ERROR, "email already exist");
            }
        }
    }

    private void createUser(RegisterRequest request) {
        User user = new User();
        user.setUid(idService.generateUid());
        user.setUname(request.getUname());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setStatus(UserStatusEnum.NORMAL);
        userRepository.save(user);

        Auth auth = new Auth();
        auth.setUid(user.getUid());
        auth.setType(AuthTypeEnum.LOCAL);
        auth.setIdentifier(String.valueOf(user.getUid()));
        auth.setCredential(DigestUtils.md5DigestAsHex(request.getPassword().getBytes()));
        authRepository.save(auth);

        contextHolder.getRegisterContext().setUser(user);
    }

    private RegisterResponse buildResponse() {
        User user = contextHolder.getRegisterContext().getUser();
        RegisterResponse response = new RegisterResponse();
        response.setUid(user.getUid());
        return response;
    }

}
