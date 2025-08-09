package com.emlynma.ms.core.util;

import com.emlynma.ms.core.component.spring.ApplicationContextHolder;
import com.emlynma.ms.core.enums.EnvEnum;
import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;

@UtilityClass
public class SpringUtils {

    public static <T> T getBean(Class<T> type) {
        return getApplicationContext().getBean(type);
    }

    public static <T> T getBean(String name, Class<T> type) {
        return getApplicationContext().getBean(name, type);
    }

    public static String getEnv() {
        return getApplicationContext().getEnvironment().getActiveProfiles()[0];
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

    private static ApplicationContext getApplicationContext() {
        return ApplicationContextHolder.getApplicationContext();
    }

}
