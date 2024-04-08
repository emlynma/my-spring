package com.emlynma.spring.data.repository;

import com.emlynma.spring.data.entity.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> { }
