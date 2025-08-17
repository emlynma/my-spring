package com.emlynma.ms.data.lock;

public interface LockExecutor {

    int lock(LockParams lockParams);

    int unlock(LockParams lockParams);

}
