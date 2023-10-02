package emlyn.ma.my.spring.beans.factory;

import emlyn.ma.my.spring.joke.common.client.JokeApiClient;
import emlyn.ma.my.spring.joke.service.JokeService;
import emlyn.ma.my.spring.joke.service.impl.JokeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.metrics.StartupStep;

public class BeanFactoryTests {

    @Test
    void test_Singleton_Prototype() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("jokeApiClient", BeanDefinitionBuilder
                .genericBeanDefinition(JokeApiClient.class )
                .setScope(AbstractBeanFactory.SCOPE_SINGLETON)
                .getBeanDefinition());
        beanFactory.registerBeanDefinition("jokeService", BeanDefinitionBuilder
                .genericBeanDefinition(JokeServiceImpl.class)
                .setScope(AbstractBeanFactory.SCOPE_PROTOTYPE)
                .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR)
                .getBeanDefinition());
        JokeApiClient jokeApiClient1 = beanFactory.getBean(JokeApiClient.class);
        JokeApiClient jokeApiClient2 = beanFactory.getBean(JokeApiClient.class);
        Assertions.assertSame(jokeApiClient1, jokeApiClient2);
        JokeServiceImpl jokeService1 = beanFactory.getBean(JokeServiceImpl.class);
        JokeServiceImpl jokeService2 = beanFactory.getBean(JokeServiceImpl.class);
        Assertions.assertNotSame(jokeService1, jokeService2);
    }

    @Test
    void test_InitializingBean_DisposableBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("myLifecycleCallbackBean", BeanDefinitionBuilder
                .genericBeanDefinition(MyLifecycleCallbackBean.class)
                .getBeanDefinition());
        MyLifecycleCallbackBean myLifecycleCallbackBean = beanFactory.getBean(MyLifecycleCallbackBean.class);
        Assertions.assertNotNull(myLifecycleCallbackBean);
        beanFactory.destroyBean(myLifecycleCallbackBean);
    }

    @Test
    void test_FactoryBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("myJokeService", BeanDefinitionBuilder
                .genericBeanDefinition(MyJokeServiceFactoryBean.class)
                .getBeanDefinition());
        JokeService jokeService = (JokeService) beanFactory.getBean("myJokeService");
        Assertions.assertNotNull(jokeService);
        MyJokeServiceFactoryBean jokeServiceFactory = (MyJokeServiceFactoryBean) beanFactory.getBean("&myJokeService");
        Assertions.assertNotNull(jokeServiceFactory);
    }

    @Test
    void test_BeanFactoryMethod() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("myJokeServiceFactory", BeanDefinitionBuilder
                .genericBeanDefinition(MyJokeServiceFactory.class)
                .getBeanDefinition());
        beanFactory.registerBeanDefinition("myJokeService", BeanDefinitionBuilder
                .genericBeanDefinition(JokeService.class)
                .setFactoryMethodOnBean("getJokeService", "myJokeServiceFactory")
                .getBeanDefinition());
        JokeService jokeService = beanFactory.getBean(JokeService.class);
        Assertions.assertNotNull(jokeService);
    }

    @Test
    void test_InstanceSupplier() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("myJokeService", BeanDefinitionBuilder
                .genericBeanDefinition(JokeService.class, () ->
                        new JokeServiceImpl(new JokeApiClient()))
                .getBeanDefinition());
        JokeService jokeService = beanFactory.getBean(JokeService.class);
        Assertions.assertNotNull(jokeService);
    }

    @Test
    void test_BeanPostProcessor() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("myLifecycleCallbackBean", BeanDefinitionBuilder
                .genericBeanDefinition(MyLifecycleCallbackBean.class)
                .getBeanDefinition());
        beanFactory.addBeanPostProcessor(new MyTraceBeanPostProcessor());
        MyLifecycleCallbackBean myLifecycleCallbackBean = beanFactory.getBean(MyLifecycleCallbackBean.class);
        Assertions.assertNotNull(myLifecycleCallbackBean);
    }

    @Test
    void test_BeanFactoryPostProcessor() {
        System.out.println("DefaultListableBeanFactory not support BeanFactoryPostProcessor");
    }

    @Test
    void test_ExistingObject() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("jokeApiClient", new JokeApiClient());
        JokeApiClient jokeApiClient = beanFactory.getBean(JokeApiClient.class);
        Assertions.assertNotNull(jokeApiClient);
    }

    public static class MyLifecycleCallbackBean implements InitializingBean, DisposableBean {
        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("MyLifecycleCallbackBean#afterPropertiesSet");
        }
        @Override
        public void destroy() throws Exception {
            System.out.println("MyLifecycleCallbackBean#destroy");
        }
    }

    public static class MyJokeServiceFactory {
        public JokeService getJokeService() throws Exception {
            return new JokeServiceImpl(new JokeApiClient());
        }
    }

    public static class MyJokeServiceFactoryBean implements FactoryBean<JokeService> {
        @Override
        public JokeService getObject() throws Exception {
            return new JokeServiceImpl(new JokeApiClient());
        }
        @Override
        public Class<?> getObjectType() {
            return JokeService.class;
        }
    }

    public static class MyTraceBeanPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            System.out.println(beanName + " bean is created");
            return bean;
        }
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            System.out.println(beanName + " bean is initialized");
            return bean;
        }
    }

    public static class MyTraceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            beanFactory.addBeanPostProcessor(new MyTraceBeanPostProcessor());
            System.out.println("MyTraceBeanFactoryPostProcessor#postProcessBeanFactory: add MyTraceBeanPostProcessor");
        }
    }

    public static class MyApplicationSetup implements ApplicationStartup {
        @Override
        public StartupStep start(String name) {
            return null;
        }
    }
}
