package com.yonyou.openapi.oauth.service;

import com.yonyou.mcloud.memcached.MemcachedUtils;
import com.yonyou.openapi.oauth.OAuthException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static com.yonyou.openapi.oauth.impl.OAuthErrorCode.OAEC_LACK_PARAM;

/**
 * Created by hubo on 2016/2/24
 */
public class CodeService {

    private static final Logger logger =
            LoggerFactory.getLogger(CodeService.class);

    /**
     * 定义授权码计数器在memcached的键名称
     */
    public static final String CODE_COUNTER = "code_counter";

    /**
     * code前缀
     */
    public static final String CODE_PREFIX = "cd_";

    /**
     * 定义授权码过期时间，默认为10分钟
     */
    public static final int CODE_EXPIRE_IN = 10 * 60;

    /**
     * 检验code码
     * @param clientId 客户端ID
     * @param code 传进来的code码
     * @return 是否正确
     */
    public boolean validateCode(String clientId, String code) throws OAuthException{

        if(StringUtils.isEmpty(clientId) || StringUtils.isEmpty(code)) {
            throw new OAuthException(OAEC_LACK_PARAM);
        }

        String saveClientId = MemcachedUtils.get(CODE_PREFIX + code);

        if(StringUtils.isEmpty(saveClientId) || !saveClientId.equals(clientId)) {
            return false;
        }

        MemcachedUtils.remove(CODE_PREFIX + code);

        return true;
    }

    public String createCode(String clientId) throws OAuthException {

        if(StringUtils.isEmpty(clientId)) {
            throw new OAuthException(OAEC_LACK_PARAM);
        }

        String code = Long.toString(
                MemcachedUtils.incr(CODE_COUNTER, 1, 0) + (new Date()).getTime(), 24);

        if (!MemcachedUtils.set(CODE_PREFIX + code, clientId, CODE_EXPIRE_IN)) {
            return null;
        }

        return code;
    }

}
