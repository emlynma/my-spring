package com.emlynma.spring.user;

import com.emlynma.spring.core.util.JsonUtils;
import com.emlynma.spring.core.component.redis.RedisClient;
import com.emlynma.spring.data.entity.User;
import com.emlynma.spring.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserApplicationTests {


    @Autowired
    private RedisClient redisClient;

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() throws InterruptedException {
    }

    @Test
    void testRedisUtils() {
        redisClient.set("test", "123456", 10);
        System.out.println(redisClient.get("test"));
    }

    @Test
    void testUserService() {
        User user = userRepository.findByUid(1710121088L);
        System.out.println(JsonUtils.toJson(user));
    }

}
