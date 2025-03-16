package com.emlynma.spring.data.repository;

import com.emlynma.spring.data.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthRepository {

    private final AuthMapper authMapper;

}
