package com.emlynma.spring.user;

import com.emlynma.spring.core.util.JsonUtils;
import com.emlynma.spring.core.util.RedisUtils;
import com.emlynma.spring.data.entity.User;
import com.emlynma.spring.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserApplicationTests {


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() throws InterruptedException {
    }

    @Test
    void testRedisUtils() {
        redisUtils.set("test", "123456", 10);
        System.out.println(redisUtils.get("test"));
    }

    @Test
    void testUserService() {
        User user = userRepository.findByUid(1710121088L);
        System.out.println(JsonUtils.toJson(user));
    }

}
