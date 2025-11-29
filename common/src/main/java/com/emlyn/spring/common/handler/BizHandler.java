package com.emlyn.spring.common.handler;

public interface BizHandler<Request, Response> {

    Response handle(Request request);

}
