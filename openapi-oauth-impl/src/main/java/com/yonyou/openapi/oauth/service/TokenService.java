package com.yonyou.openapi.oauth.service;

import com.yonyou.mcloud.memcached.MemcachedUtils;
import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.OAuthToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static com.yonyou.openapi.oauth.impl.OAuthErrorCode.OAEC_LACK_PARAM;

/**
 * Created by hubo on 2016/2/24
 */
public class TokenService {

    private Logger logger = LoggerFactory.getLogger(TokenService.class);

    /**
     * access_token计数器，用于生成唯一的access_token
     */
    public static final String ACCESS_TOKEN_COUNTER = "access_token_counter";

    /**
     * access_token前缀
     */
    public static final String ACCESS_TOKEN_PREFIX = "at_";

    /**
     * refresh_token前缀
     */
    public static final String REFRESH_TOKEN_PREFIX = "rt_";

    /**
     * access_token索引前缀，通过client_id,userid查找access_token
     */
    public static final String ACCESS_TOKEN_INDEX_PREFIX = "ati_";

    public static final int EXPIRESIN = 3600 * 2;

    public static final int REFRESH_EXPIRESIN = 3600 * 24;

    public OAuthToken createOAuthToken(String clientId, String grantType) throws OAuthException{

        if(StringUtils.isEmpty(clientId) || StringUtils.isEmpty(grantType)) {
            throw new OAuthException(OAEC_LACK_PARAM);
        }

        OAuthToken token = new OAuthToken();
        token.setScope(grantType);
        token.setExpiresIn(EXPIRESIN);
        token.setTokenType(grantType);

        String accessToken = DigestUtils.sha1Hex(Long.toString(MemcachedUtils.incr(ACCESS_TOKEN_COUNTER, 1, 0)
                + (new Date()).getTime(), 24));

        token.setAccessToken(accessToken);

        if (!MemcachedUtils.set(ACCESS_TOKEN_PREFIX + token.getAccessToken(),
                clientId, EXPIRESIN)) {
            logger.error("创建access_token失败!");
            return null;
        }

        if(grantType.equals("authorization_code") || grantType.equals("password")) {

            String refreshToken = DigestUtils.sha1Hex(Long.toString(MemcachedUtils.incr(ACCESS_TOKEN_COUNTER, 1, 0)
                    + (new Date()).getTime(), 24));

            token.setRefreshToken(refreshToken);

            if (!MemcachedUtils.set(REFRESH_TOKEN_PREFIX + token.getRefreshToken(),
                    clientId, REFRESH_EXPIRESIN)) {
                logger.error("创建refresh_token失败!");
                return null;
            }
        }

        return token;
    }


    public boolean vaildateToke(String accessToke) {
        return MemcachedUtils.get(ACCESS_TOKEN_PREFIX + accessToke) != null;
    }

    public boolean vaildateRefreshToke(String refreshToke) {
        return MemcachedUtils.get(REFRESH_TOKEN_PREFIX + refreshToke) != null;
    }

}
