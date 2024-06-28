package com.naraci.core.aop;

import com.naraci.core.entity.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @author ShenZhaoYu
 * @date 2024/2/14
 */
@RestControllerAdvice
@Slf4j
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception e) {
        log.error("系统异常", e);
        return new ApiResponse(1, "请联系系统管理员", "请联系系统管理员");
    }

    @ExceptionHandler(CustomException.class)
    public ApiResponse handleException(CustomException e) {
        log.error("业务异常", e);
        return new ApiResponse(e.getCode(), e.getMsg(), e.getData());
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ApiResponse handleException(org.springframework.web.bind.MethodArgumentNotValidException e) {
        log.error("参数校验失败", e);
        return new ApiResponse(1, e.getBindingResult().getFieldError().getDefaultMessage(), null);
    }
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        HandlerMethod handlerMethod = (HandlerMethod) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        String controllerClass = handlerMethod.getBeanType().getName();

        // 判断是否为指定控制器
        // 各模块写控制器都需要写到这个包下面
        return controllerClass.startsWith("com.naraci.app");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ApiResponse) {
            return body;
        }
        return new ApiResponse(200, "success", body);
    }
}
