package com.emlynma.spring.user.service;

import com.emlynma.spring.core.component.id.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdService {

    private final IdGenerator idGenerator;

    public Long generateUid() {
        return idGenerator.generateId();
    }

}
