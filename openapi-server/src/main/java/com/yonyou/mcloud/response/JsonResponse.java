package com.yonyou.mcloud.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yonyou.mcloud.exception.BusinessException;
import com.yonyou.mcloud.utils.MessageUtils;

import java.io.Serializable;

/**
 * 定义RESTful服务数据返回格式。
 * 
 * @author wangshqb
 * 
 */
public class JsonResponse<T> implements Serializable {



    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final String SUCCESS = "000000";

    /**
     * 失败
     */
    public static final String ERROR = "100000";

    /**
     * 返回编码
     */
    @JsonProperty(value = "flag")
    private String retcode;

    /**
     * 返回消息
     */
    @JsonProperty(value = "desc")
    private String retmsg;

    /**
     * 返回请求的数据
     */
    @JsonProperty(value = "data")
    private T data;

    /**
     * 构造指定编码和消息的返回对象。
     * 
     * @param code
     * @param message
     * @param data
     */
    protected JsonResponse(String code, String message, T data) {
        this.retcode = code;
        this.retmsg = message;
        this.data = data;
    }

    public String getRetcode() {
        return this.retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return this.retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 创建请求成功时返回结果对象。
     * 
     * @return
     */
    public static <T> JsonResponse<T> success(T data) {
        return new JsonResponse<T>(SUCCESS, "success", data);
    }

    /**
     * 处理指定异常的返回结果。
     * 
     * @param e
     * @return
     */
    public static <T> JsonResponse<T> error(Throwable e) {
        // 处理业务异常
        if (e instanceof BusinessException) {
            String code = ((BusinessException) e).getCode();
            return error(code, e.getMessage());
        }
        return error(ERROR, e.toString());
    }

    /**
     * 处理指定错误码的返回结果。
     * 
     * @param code
     * @return
     */
    public static <T> JsonResponse<T> error(String code) {
        return error(new BusinessException(code));
    }

    /**
     * 处理指定错误码的返回结果。
     * 
     * @param code
     * @param args
     * @return
     */
    public static <T> JsonResponse<T> error(String code, Object[] args) {
        return error(new BusinessException(code, args));
    }

    /**
     * 处理指定错误码和消息的返回结果。
     * 
     * @param code
     * @param message
     * @return
     */
    public static <T> JsonResponse<T> error(String code, String message) {
        return error(code, message, null);
    }

    /**
     * 指定错误消息及具体的数据。
     * 
     * @param code
     * @param message
     * @param data
     * @return JsonResponse<T>
     */
    public static <T> JsonResponse<T> error(String code, String message,
                                            T data) {
        return new JsonResponse<T>(code, message, data);
    }

    /**
     * 处理指定状态码的返回结果。
     * 
     * @param code
     * @param args
     * @param data
     * @return
     */
    public static <T> JsonResponse<T> info(String code, Object[] args, T data) {
        String message = MessageUtils.getMessage(code, args);
        return new JsonResponse<T>(code, message, data);
    }

}
