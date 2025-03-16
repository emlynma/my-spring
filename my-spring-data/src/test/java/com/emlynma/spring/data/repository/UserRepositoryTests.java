package com.emlynma.spring.data.repository;

import com.emlynma.spring.core.id.IdGenerator;
import com.emlynma.spring.core.util.JsonUtils;
import com.emlynma.spring.data.entity.User;
import com.emlynma.spring.data.entity.enums.UserStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdGenerator idGenerator;

    @Test
    void testInsert() {
        User user = new User();
        user.setUid(idGenerator.generateUid());
        user.setUname("emlynma");
        user.setPhone("186123");
        user = userRepository.saveAndLoad(user);
        System.out.println(JsonUtils.toJson(user));
    }

    @Test
    void testSave() {
        User user = userRepository.findByUid(292077809260695552L);
        user.setStatus(UserStatusEnum.DISABLED);
        userRepository.save(user);
        user = userRepository.findByUid(292077809260695552L);
        System.out.println(JsonUtils.toJson(user));
    }

}
