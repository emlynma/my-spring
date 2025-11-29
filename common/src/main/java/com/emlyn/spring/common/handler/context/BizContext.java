package com.emlyn.spring.common.handler.context;

public interface BizContext<Request, Response> {

    Request getRequest();

    void setRequest(Request request);

    Response getResponse();

    void setResponse(Response response);

}
