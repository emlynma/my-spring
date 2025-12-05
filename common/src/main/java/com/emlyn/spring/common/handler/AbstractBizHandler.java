package com.emlyn.spring.common.handler;

import com.emlyn.spring.common.exception.BizException;
import com.emlyn.spring.common.exception.SysException;
import com.emlyn.spring.common.handler.exception.BizExceptionDispatcher;
import com.emlyn.spring.common.handler.lock.Lock;
import com.emlyn.spring.common.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public abstract class AbstractBizHandler<Request, Response> implements BizHandler<Request, Response> {

    protected final ThreadLocal<Lock> LockHolder = new ThreadLocal<>();

    @Override
    public Response handle(Request request) {
        try {

            return doHandle(request);

        } catch (BizException e) {

            return handleBizException(e);

        } catch (SysException e) {

            return handleSysException(e);

        } catch (Exception e) {

            log.error("unknown exception", e);
            throw e;

        } finally {
            unlock();
        }
    }

    public void locked(Lock lock) {
        LockHolder.set(lock);
        lock.locked();
    }

    private void unlock() {
        var lock = LockHolder.get();
        if (lock != null) {
            lock.unlock();
        }
        LockHolder.remove();
    }

    protected abstract Response doHandle(Request request);

    private Response handleBizException(BizException exception) {
        return SpringUtils.getBean(BizExceptionDispatcher.class).dispatch(this, exception);
    }

    private Response handleSysException(SysException exception) {
        throw exception;
    }

}
