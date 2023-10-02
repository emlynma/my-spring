package emlyn.ma.my.spring.aop;

import emlyn.ma.my.spring.joke.common.client.JokeApiClient;
import emlyn.ma.my.spring.joke.service.JokeService;
import emlyn.ma.my.spring.joke.service.impl.JokeServiceImpl;
import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class AdvisedSupportTests {

    @Test
    void testProxyFactory() {

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new JokeServiceImpl(new JokeApiClient()));

        proxyFactory.addAdvice((MethodInterceptor) invocation -> {
            System.out.println("Before method: " + invocation.getMethod().getName());
            Object result = invocation.proceed();
            System.out.println("After method: " + invocation.getMethod().getName());
            return result;
        });

        JokeService jokeService = (JokeService) proxyFactory.getProxy();

        System.out.println(jokeService.getJokeList(1));
    }

    @Test
    void testProxyFactoryBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("jokeApiClient", BeanDefinitionBuilder
                .genericBeanDefinition(JokeApiClient.class)
                .getBeanDefinition());
        beanFactory.registerBeanDefinition("jokeService", BeanDefinitionBuilder
                .genericBeanDefinition(JokeServiceImpl.class)
                .setAutowireMode(ConfigurableListableBeanFactory.AUTOWIRE_CONSTRUCTOR)
                .getBeanDefinition());

        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setBeanFactory(beanFactory);
        proxyFactoryBean.setTargetName("jokeService");
        proxyFactoryBean.addAdvice((MethodInterceptor) invocation -> {
            System.out.println("Before method: " + invocation.getMethod().getName());
            Object result = invocation.proceed();
            System.out.println("After method: " + invocation.getMethod().getName());
            return result;
        });

        JokeService jokeService = (JokeService) proxyFactoryBean.getObject();
        assert jokeService != null;
        System.out.println(jokeService.getJokeList(1));
    }

    @Test
    void testAspectJProxyFactory() {
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        proxyFactory.setTarget(new JokeServiceImpl(new JokeApiClient()));
        proxyFactory.addAspect(MyAspect.class);
        JokeService jokeService = proxyFactory.getProxy();

        System.out.println(jokeService.getJokeList(1));
    }

    @Aspect
    private static class MyAspect {

        @Pointcut("execution(* emlyn.ma.my.spring.joke.service.JokeService.getJokeList(..))")
        private void pointcut() { }

        @Before("pointcut()")
        public void before(JoinPoint joinPoint) {
            System.out.println("Before method: " + joinPoint.getSignature().getName());
        }

        @After("pointcut()")
        public void after(JoinPoint joinPoint) {
            System.out.println("After method: " + joinPoint.getSignature().getName());
        }
    }

}
