package com.yonyou.mcloud.controller;

import com.yonyou.mcloud.exception.BusinessException;
import com.yonyou.mcloud.response.JsonResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制器抽象类。
 * 
 * <ul>
 * <li>统一异常处理；
 * <li>统一数据返回格式
 * </ul>
 * 
 * @author hubo
 * 
 */
public abstract class BasicController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 异常统一处理。
     */
    @ExceptionHandler
    @ResponseBody
    public Object handleException(HttpServletRequest request, Exception ex) {
        // root cause
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        // 记录最终需要处理的异常
        Throwable handleEx = rootCause != null ? rootCause : ex;
        // 日志记录，业务异常日志警告
        if (handleEx instanceof BusinessException) {
            String code = ((BusinessException) handleEx).getCode();
            logger.warn(code + ": " + handleEx.getMessage());
        }
        else {
            // 非业务异常报警处理
            logger.error(handleEx.getMessage(), handleEx);
        }
        // 统一错误返回格式
        return error(handleEx);
    }

    /**
     * 创建成功时返回结果对象。
     * 
     * @return JsonResponse<T>
     */
    protected <T> JsonResponse<T> success() {
        return success(null);
    }

    /**
     * 创建成功时返回结果对象。
     * 
     * @param data
     * @return JsonResponse<T>
     */
    protected <T> JsonResponse<T> success(T data) {
        return JsonResponse.success(data);
    }

    /**
     * 处理指定异常的返回结果。
     * 
     * @param e
     * @return JsonResponse<T>
     */
    protected <T> JsonResponse<T> error(Throwable e) {
        return JsonResponse.error(e);
    }

    /**
     * 处理指定错误码的返回结果。
     * 
     * @param code
     * @return JsonResponse<T>
     */
    protected <T> JsonResponse<T> error(String code) {
        return JsonResponse.error(code);
    }

    /**
     * 处理指定错误码的返回结果。
     * 
     * @param code
     * @param args
     * @return JsonResponse<T>
     */
    protected <T> JsonResponse<T> error(String code, Object[] args) {
        return JsonResponse.error(code, args);
    }

    /**
     * 处理指定错误码和消息的返回结果。
     * 
     * @param code
     * @param message
     * @return JsonResponse<T>
     */
    protected <T> JsonResponse<T> error(String code, String message) {
        return JsonResponse.error(code, message);
    }

    /**
     * 指定错误消息及具体的数据。
     * 
     * @param code
     * @param message
     * @param data
     * @return JsonResponse<T>
     */
    protected <T> JsonResponse<T> error(String code, String message, T data) {
        return JsonResponse.error(code, message, data);
    }

}
