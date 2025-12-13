package com.emlyn.spring.common.component.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.locks.Lock;

@Slf4j
@SpringBootTest
public class SimpleRedisLockTest {

    @Test
    void testLock() throws Exception {
        Lock lock = new SimpleRedisLock("test-lock-key");

        Runnable task = () -> {
            lock.lock();
            try {
                log.info("task start");
                Thread.sleep(1000);
                log.info("task end");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lock.unlock();
        };

        new Thread(task).start();
        new Thread(task).start();

        Thread.sleep(3000);
    }

}
