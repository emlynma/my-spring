package com.emlynma.spring.core.advice;

import com.emlynma.spring.core.ApiResponse;
import com.emlynma.spring.core.util.JsonUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@Order(1)
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@NonNull MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {

        if (selectedConverterType.isAssignableFrom(ByteArrayHttpMessageConverter.class)) {
            return body;
        }

        ApiResponse<?> apiResponse = this.buildApiResponse(body);

        return selectedConverterType.isAssignableFrom(StringHttpMessageConverter.class)
                ? JsonUtils.toJson(apiResponse)
                : apiResponse;
    }

    private ApiResponse<?> buildApiResponse(Object body) {
        if (body == null) {
            return ApiResponse.success(null);
        } else if (body instanceof ApiResponse<?>) {
            return (ApiResponse<?>) body;
        } else {
            return ApiResponse.success(body);
        }
    }

}
