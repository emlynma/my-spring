package emlyn.ma.my.spring.beans.factory;

import emlyn.ma.my.spring.joke.common.client.JokeApiClient;
import emlyn.ma.my.spring.joke.service.JokeService;
import emlyn.ma.my.spring.joke.service.impl.JokeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;
import org.springframework.core.AliasRegistry;
import org.springframework.core.SimpleAliasRegistry;
import org.springframework.util.ClassUtils;

public class DefaultListableBeanFactoryTests {

    /**
     * 别名注册表
     * AliasRegistry 的直接实现是 SimpleAliasRegistry，核心数据结构是 aliasMap，是一个 Map<String, String>，key 是别名，value 是 beanName。
     * DefaultSingletonBeanRegistry 继承自 SimpleAliasRegistry，所以 DefaultSingletonBeanRegistry 也具有别名注册的功能
     */
    @Test
    void testAliasRegistry() {
        AliasRegistry aliasRegistry = new SimpleAliasRegistry();
        aliasRegistry.registerAlias("jokeServiceImpl", "jokeService");
        Assertions.assertTrue(aliasRegistry.isAlias("jokeService"));
        aliasRegistry.removeAlias("jokeService");
    }

    /**
     * 单例 Bean 注册表
     * SingletonBeanRegistry 的直接实现是 DefaultSingletonBeanRegistry，核心数据结构是三个 Map，
     * 分别是 singletonObjects、earlySingletonObjects、singletonFactories，又被称为三级缓存。
     * * singletonObjects：存放完全初始化好的单例 Bean，被称为一级缓存
     * * earlySingletonObjects：存放早期暴露的单例 Bean，被称为二级缓存
     * * singletonFactories：存放 ObjectFactory，用于创建 Bean，被称为三级缓存
     * 这个类的作用是注册单例 Bean，单例 Bean 是指在整个容器中只有一个实例的 Bean。
     * 另外这个类非常重要，是 Spring 单例 Bean 的核心，也是 Spring 事务的核心。
     * DefaultListableBeanFactory 是 DefaultSingletonBeanRegistry 的子类，拥有其所有的功能。
     */
    @Test
    void testSingletonBeanRegistry() {
        SingletonBeanRegistry singletonBeanRegistry = new DefaultSingletonBeanRegistry();
        singletonBeanRegistry.registerSingleton("jokeService", new JokeServiceImpl(null));
        Object jokeService = singletonBeanRegistry.getSingleton("jokeService");
        Assertions.assertNotNull(jokeService);
        Assertions.assertArrayEquals(new String[]{"jokeService"}, singletonBeanRegistry.getSingletonNames());
    }

    /**
     * FactoryBeanRegistrySupport 的直接实现是 DefaultListableBeanFactory，
     * 单例 Bean 注册表支持 FactoryBean，核心数据结构是 factoryBeanObjectCache，是一个 Map<String, Object>，
     * key 是 beanName，value 是 FactoryBean，对于这种 Bean，getBean()方法会返回 FactoryBean::getObject() 方法的返回值。
     * DefaultListableBeanFactory 是 FactoryBeanRegistrySupport 的子类，拥有其所有的功能。
     */
    @Test
    void testFactoryBeanRegistrySupport() {
        FactoryBeanRegistrySupport factoryBeanRegistry = new DefaultListableBeanFactory();
        FactoryBean<JokeService> jokeServiceFactoryBean = new FactoryBean<>() {
            @Override
            public JokeService getObject() {
                return new JokeServiceImpl(null);
            }
            @Override
            public Class<?> getObjectType() {
                return JokeService.class;
            }
        };
        factoryBeanRegistry.registerSingleton("jokeService", jokeServiceFactoryBean);
        Object jokeService = ((BeanFactory) factoryBeanRegistry).getBean("jokeService");
        Assertions.assertNotNull(jokeService);
        Assertions.assertEquals(JokeServiceImpl.class, jokeService.getClass());
        Object bean = ((BeanFactory) factoryBeanRegistry).getBean("&jokeService");
        Assertions.assertNotNull(bean);
        Assertions.assertTrue(bean instanceof FactoryBean);
    }

    /**
     * Bean 定义注册表
     * BeanDefinitionRegistry 的直接实现是 DefaultListableBeanFactory，
     * 核心数据结构是 beanDefinitionMap，是一个 Map<String, BeanDefinition>，key 是 beanName，value 是 BeanDefinition
     * 这个接口的作用是注册 BeanDefinition，BeanDefinition 是 Bean 的定义，包含了 Bean 的所有属性。
     */
    @Test
    void testBeanDefinitionRegistry() {
        BeanDefinitionRegistry beanDefinitionRegistry = new DefaultListableBeanFactory();
        BeanDefinition jokeApiClientDefinition = new GenericBeanDefinition();
        jokeApiClientDefinition.setBeanClassName(JokeApiClient.class.getName());
        BeanDefinition jokeServiceDefinition = new GenericBeanDefinition();
        jokeServiceDefinition.setBeanClassName(JokeServiceImpl.class.getName());
        jokeServiceDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, new RuntimeBeanReference("jokeApiClient"));
        beanDefinitionRegistry.registerBeanDefinition("jokeApiClient", jokeApiClientDefinition);
        beanDefinitionRegistry.registerBeanDefinition("jokeService", jokeServiceDefinition);
        Object jokeService = ((BeanFactory) beanDefinitionRegistry).getBean("jokeService");
        Assertions.assertNotNull(jokeService);
        Assertions.assertEquals(JokeServiceImpl.class, jokeService.getClass());
    }

    /**
     * Bean 工厂
     * BeanFactory 是 Spring 的核心接口，是 Spring 框架的基础设施，面向 Spring 本身，被 Spring 本身使用。
     * BeanFactory 的直接实现是 DefaultListableBeanFactory，DefaultListableBeanFactory 是 DefaultSingletonBeanRegistry 的子类，
     * DefaultSingletonBeanRegistry 又是 SimpleAliasRegistry 的子类，所以 BeanFactory 间接实现了 AliasRegistry 和 SingletonBeanRegistry。
     * BeanFactory 的核心功能是管理 Bean 的生命周期，包括 Bean 的加载、注册、实例化、配置、初始化、销毁等。
     * BeanFactory 的另一个重要功能是依赖注入，包括构造器注入、setter 注入、字段注入等。
     * BeanFactory 的另一个重要功能是 AOP，BeanFactory 会为 Bean 创建代理对象。
     */
    @Test
    void testBeanFactory() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition("jokeApiClient",
                BeanDefinitionBuilder.genericBeanDefinition(JokeApiClient.class).getBeanDefinition());
        BeanFactory beanFactory = new DefaultListableBeanFactory(defaultListableBeanFactory);
        // getBean
        JokeApiClient jokeApiClient = beanFactory.getBean("jokeApiClient", JokeApiClient.class);
        Assertions.assertNotNull(jokeApiClient);
        // getBeanProvider
        ObjectProvider<JokeApiClient> beanProvider = beanFactory.getBeanProvider(JokeApiClient.class);
        JokeApiClient client = beanProvider.getObject();
        Assertions.assertEquals(jokeApiClient, client);
        // containsBean
        Assertions.assertTrue(beanFactory.containsBean("jokeApiClient"));
        // isSingleton
        Assertions.assertTrue(beanFactory.isSingleton("jokeApiClient"));
        // isPrototype
        Assertions.assertFalse(beanFactory.isPrototype("jokeApiClient"));
        // isTypeMatch
        Assertions.assertTrue(beanFactory.isTypeMatch("jokeApiClient", JokeApiClient.class));
        // getType
        Assertions.assertEquals(JokeApiClient.class, beanFactory.getType("jokeApiClient"));
        // getAliases
        Assertions.assertArrayEquals(new String[]{}, beanFactory.getAliases("jokeApiClient"));
    }

    /**
     * 可列举的 Bean 工厂
     * ListableBeanFactory 的直接实现是 DefaultListableBeanFactory
     * ListableBeanFactory 的作用是列举 Bean，也就是获取容器中所有的 Bean。
     */
    @Test
    void testListableBeanFactory() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition("jokeApiClient",
                BeanDefinitionBuilder.genericBeanDefinition(JokeApiClient.class).getBeanDefinition());
        ListableBeanFactory listableBeanFactory = defaultListableBeanFactory;
        // getBeanDefinitionCount
        Assertions.assertEquals(1, listableBeanFactory.getBeanDefinitionCount());
        // getBeanDefinitionNames
        Assertions.assertArrayEquals(new String[]{"jokeApiClient"}, listableBeanFactory.getBeanDefinitionNames());
        // getBeanNamesForType
        Assertions.assertArrayEquals(new String[]{"jokeApiClient"}, listableBeanFactory.getBeanNamesForType(JokeApiClient.class));
        // getBeansOfType
        Assertions.assertEquals(1, listableBeanFactory.getBeansOfType(JokeApiClient.class).size());
    }

    /**
     * 可配置的 Bean 工厂
     * ConfigurableBeanFactory 的直接实现是 DefaultListableBeanFactory
     * ConfigurableBeanFactory 的作用是配置 Bean 工厂，也就是设置 Bean 工厂的各种属性。
     */
    @Test
    void testConfigurableBeanFactory() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition("jokeApiClient",
                BeanDefinitionBuilder.genericBeanDefinition(JokeApiClient.class).getBeanDefinition());

        ConfigurableBeanFactory configurableBeanFactory = new DefaultListableBeanFactory();
        // setParentBeanFactory
        configurableBeanFactory.setParentBeanFactory(defaultListableBeanFactory);
        // setBeanClassLoader
        configurableBeanFactory.setBeanClassLoader(ClassUtils.getDefaultClassLoader());
        // setTempClassLoader
        // configurableBeanFactory.setTempClassLoader(null);
        // setCacheBeanMetadata
        // configurableBeanFactory.setCacheBeanMetadata(false);
        // setBeanExpressionResolver
        // configurableBeanFactory.setBeanExpressionResolver(null);
        // setConversionService
        // configurableBeanFactory.setConversionService(null);
        // addPropertyEditorRegistrar
        // configurableBeanFactory.addPropertyEditorRegistrar(null);
        // registerCustomEditor
        // configurableBeanFactory.registerCustomEditor(null, null);
        // copyRegisteredEditorsTo
        // configurableBeanFactory.copyRegisteredEditorsTo(null);
        // setTypeConverter
        // configurableBeanFactory.setTypeConverter(null);
        // addEmbeddedValueResolver
        // configurableBeanFactory.addEmbeddedValueResolver(null);
        // setApplicationStartup
        // configurableBeanFactory.setApplicationStartup(null);
        // addBeanPostProcessor
        // configurableBeanFactory.addBeanPostProcessor(null);
        JokeApiClient jokeApiClient = configurableBeanFactory.getBean("jokeApiClient", JokeApiClient.class);
        Assertions.assertNotNull(jokeApiClient);
    }

    /**
     * 可自动装配的 Bean 工厂
     * AutowireCapableBeanFactory 的直接实现是 AbstractAutowireCapableBeanFactory
     * AutowireCapableBeanFactory 的作用是自动装配 Bean，也就是自动为 Bean 的属性赋值。
     */
    @Test
    void testAutowireCapableBeanFactory() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition("jokeApiClient",
                BeanDefinitionBuilder
                        .genericBeanDefinition(JokeApiClient.class)
                        .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR)
                        .getBeanDefinition());
        defaultListableBeanFactory.registerBeanDefinition("jokeService",
                BeanDefinitionBuilder
                        .genericBeanDefinition(JokeServiceImpl.class)
                        .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR)
                        .getBeanDefinition());
        AutowireCapableBeanFactory autowireCapableBeanFactory = defaultListableBeanFactory;
        JokeService jokeService = autowireCapableBeanFactory.getBean("jokeService", JokeService.class);
        Assertions.assertNotNull(jokeService);
    }

    /**
     * 可配置的可列举的 Bean 工厂
     * ConfigurableListableBeanFactory 的直接实现是 DefaultListableBeanFactory
     * ConfigurableListableBeanFactory 的作用是配置和列举 Bean，也就是获取容器中所有的 Bean。
     */
    @Test
    void testConfigurableListableBeanFactory() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition("jokeApiClient",
                BeanDefinitionBuilder
                        .genericBeanDefinition(JokeApiClient.class)
                        .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR)
                        .getBeanDefinition());
        defaultListableBeanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) {
                System.out.println("postProcessBeforeInitialization");
                return bean;
            }
        });
        ConfigurableListableBeanFactory configurableListableBeanFactory = defaultListableBeanFactory;
        // 预加载单例 Bean
        configurableListableBeanFactory.preInstantiateSingletons();
        // getBeanDefinitionCount
        Assertions.assertEquals(1, configurableListableBeanFactory.getBeanDefinitionCount());
    }

}
