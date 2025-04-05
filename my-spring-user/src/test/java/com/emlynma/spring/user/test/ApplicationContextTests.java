package com.emlynma.spring.user.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootTest
public class ApplicationContextTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testApplication() {
        String applicationName = applicationContext.getApplicationName();
        System.out.println("applicationName = " + applicationName);
    }

    @Test
    public void testGenericApplicationContext() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.refresh();
    }

    @Test
    public void testAnnotationConfigServletWebServerApplicationContext() {
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext();
    }

}
