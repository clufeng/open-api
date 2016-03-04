package com.yonyou.mcloud.exception;

import com.yonyou.mcloud.utils.MessageUtils;

/**
 * 业务异常
 * Created by hubo on 2016/2/29
 */
public class BusinessException extends Exception{

    /**
     * 异常错误码
     */
    private String code;

    public BusinessException(final String code) {
        super(MessageUtils.getMessage(code));
        this.code = code;
    }

    public BusinessException(final String code, Object[] args) {
        super(MessageUtils.getMessage(code, args));
        this.code = code;
    }

    public BusinessException(final Throwable cause) {
        super(cause);
    }

    public BusinessException() {
        super();
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

}
