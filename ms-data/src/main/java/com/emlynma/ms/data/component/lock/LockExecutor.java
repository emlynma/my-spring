package com.emlynma.ms.data.component.lock;

public interface LockExecutor {

    int lock(LockParams lockParams);

    int unlock(LockParams lockParams);

}
