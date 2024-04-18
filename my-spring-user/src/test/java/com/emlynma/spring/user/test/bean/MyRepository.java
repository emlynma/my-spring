package com.emlynma.spring.user.test.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MyRepository {

    @Autowired(required = false)
    private MyDatabase myDatabase;

}
