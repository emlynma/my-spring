package com.emlyn.spring.data.component.lock;

public interface LockExecutor {

    int locked(String key, String val, int expire);

    int unlock(String key, String val);

}
