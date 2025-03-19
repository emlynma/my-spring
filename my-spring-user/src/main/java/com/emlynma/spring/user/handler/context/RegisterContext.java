package com.emlynma.spring.user.handler.context;

import com.emlynma.spring.data.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
@RequestScope
public class RegisterContext {

    private User user;

}
