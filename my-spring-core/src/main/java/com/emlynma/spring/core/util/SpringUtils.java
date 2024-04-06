package com.emlynma.spring.core.util;

import com.emlynma.spring.core.enums.EnvEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    public static <T> T getBean(String name, Class<T> type) {
        return applicationContext.getBean(name, type);
    }

    public static String getEnv() {
        return applicationContext.getEnvironment().getActiveProfiles()[0];
    }

    public static boolean isDevEnv() {
        return EnvEnum.DEV.getCode().equals(getEnv());
    }

    public static boolean isTestEnv() {
        return EnvEnum.TEST.getCode().equals(getEnv());
    }

    public static boolean isProdEnv() {
        return EnvEnum.PROD.getCode().equals(getEnv());
    }

}
