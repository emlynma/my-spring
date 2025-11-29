package com.emlyn.spring.common.handler;

import com.emlyn.spring.common.exception.BizException;
import com.emlyn.spring.common.exception.SysException;
import com.emlyn.spring.common.handler.context.BizContext;
import com.emlyn.spring.common.handler.exception.BizExceptionDispatcher;
import com.emlyn.spring.common.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public abstract class AbstractBizHandler<Request, Response> implements BizHandler<Request, Response> {

    protected final ThreadLocal<BizContext<Request, Response>> ContextHolder = new ThreadLocal<>();

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
            ContextHolder.remove();
        }
    }

    protected abstract Response doHandle(Request request);

    private Response handleBizException(BizException exception) {
        return SpringUtils.getBean(BizExceptionDispatcher.class).dispatch(this, exception);
    }

    private Response handleSysException(SysException exception) {
        throw exception;
    }

}
