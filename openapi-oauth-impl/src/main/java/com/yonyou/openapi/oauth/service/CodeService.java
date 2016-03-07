package com.yonyou.openapi.oauth.service;

import com.yonyou.mcloud.memcached.MemcachedUtils;
import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.model.ClientIdAndRedirectUriPair;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static com.yonyou.openapi.oauth.impl.OAuthErrorCode.*;

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
    public void validateCode(String clientId, String code, String redirectUri) throws OAuthException{

        if(StringUtils.isEmpty(clientId) || StringUtils.isEmpty(code) || StringUtils.isEmpty(redirectUri)) {
            throw new OAuthException(OAEC_LACK_PARAM);
        }

        ClientIdAndRedirectUriPair pair = MemcachedUtils.get(CODE_PREFIX + code);

        if(pair == null) {
            throw new OAuthException(OAEC_INVALID_CODE);
        }

        if(!pair.getClientId().equals(clientId)) {
            throw new OAuthException(OAEC_GRANT_TYPE_MISMATCH);
        }

        if(!pair.getRedirectUri().equals(redirectUri)) {
            throw new OAuthException(OAEC_REDIRECT_URI_MISMATCH);
        }

        MemcachedUtils.remove(CODE_PREFIX + code);
    }

    public String createCode(String clientId, String redirectUri) throws OAuthException {

        if(StringUtils.isEmpty(clientId) || StringUtils.isEmpty(redirectUri)) {
            throw new OAuthException(OAEC_LACK_PARAM);
        }

        String code = Long.toString(
                MemcachedUtils.incr(CODE_COUNTER, 1, 0) + (new Date()).getTime(), 24);

        if (!MemcachedUtils.set(CODE_PREFIX + code, new ClientIdAndRedirectUriPair(clientId, redirectUri), CODE_EXPIRE_IN)) {
            return null;
        }

        return code;
    }

}
