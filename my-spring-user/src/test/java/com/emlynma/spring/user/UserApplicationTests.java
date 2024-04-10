package com.emlynma.spring.user;

import com.emlynma.spring.core.util.JsonUtils;
import com.emlynma.spring.core.util.RedisUtils;
import com.emlynma.spring.data.entity.User;
import com.emlynma.spring.data.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserApplicationTests {


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() throws InterruptedException {
    }

    @Test
    void testUserService() {
        User user = userService.findByUid(1710121088L);
        System.out.println(JsonUtils.toJson(user));
        user.getExtraInfo().setIsStudent(true);
        user = userService.save(user);
        System.out.println(JsonUtils.toJson(user));
    }

}
