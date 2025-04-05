package com.emlynma.spring.data.repository;

import com.emlynma.spring.core.component.id.IdGenerator;
import com.emlynma.spring.core.util.JsonUtils;
import com.emlynma.spring.data.entity.User;
import com.emlynma.spring.data.entity.enums.UserStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdGenerator idGenerator;

    @Test
    void testInsert() {
        User user = new User();
        user.setUid(idGenerator.generateId());
        user.setUname("emlynma");
        user.setPhone("186123");
        userRepository.insert(user);
        user = userRepository.findByUid(user.getUid());
        System.out.println(JsonUtils.toJson(user));
    }

    @Test
    void testSave() {
        User user = userRepository.findByUid(292077809260695552L);
        User update = new User();
        update.setStatus(UserStatusEnum.DISABLED);
        update.setEmail("aaaaaaaa");
        update.setUid(292077809260695552L);

        userRepository.update(user, update);
        List<User> users = userRepository.select(user);
        System.out.println(JsonUtils.toJson(user));
    }

}
