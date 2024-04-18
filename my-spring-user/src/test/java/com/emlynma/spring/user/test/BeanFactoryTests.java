package com.emlynma.spring.user.test;

import com.emlynma.spring.user.test.bean.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class BeanFactoryTests {

    @Test
    void testGetBean() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        // 注册 bean definition
        BeanDefinitionRegistry beanDefinitionRegistry = defaultListableBeanFactory;
        beanDefinitionRegistry.registerBeanDefinition("myController", createBeanDefinition(MyController.class));
        beanDefinitionRegistry.registerBeanDefinition("myService", createBeanDefinition(MyService.class));
        beanDefinitionRegistry.registerBeanDefinition("myRepository", createBeanDefinition(MyRepository.class));
        // 注册 singleton bean
        SingletonBeanRegistry singletonBeanRegistry = defaultListableBeanFactory;
        singletonBeanRegistry.registerSingleton("myDatabase", new MyDatabase());
        // 配置 bean factory
        ConfigurableBeanFactory configurableBeanFactory = defaultListableBeanFactory;
        configurableBeanFactory.addBeanPostProcessor(new TraceBeanProcessor());

        // 使用 bean
        BeanFactory beanFactory = defaultListableBeanFactory;
        MyController myController = (MyController) beanFactory.getBean("myController");
        Assertions.assertNotNull(myController);
    }

    private BeanDefinition createBeanDefinition(Class<?> clazz) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(clazz);
        beanDefinition.setAutowireCandidate(true);
        beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        return beanDefinition;
    }

}
