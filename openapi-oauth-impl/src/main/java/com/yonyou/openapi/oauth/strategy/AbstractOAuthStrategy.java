package com.yonyou.openapi.oauth.strategy;

import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.OAuthToken;
import com.yonyou.openapi.oauth.OAuthUrl;
import com.yonyou.openapi.oauth.service.TokenService;

/**
 * Created by hubo on 2016/2/24
 */
public abstract class AbstractOAuthStrategy implements IOAuthStrategy {

    private TokenService tokenService;

    public AbstractOAuthStrategy() {
    }

    @Override
    public OAuthToken authorize(OAuthUrl url) throws OAuthException {

        authorize0(url);

        // 创建access_token
        return tokenService.createOAuthToken(url.getClientId(), url.getGrantType());
    }

    public abstract void authorize0(OAuthUrl url) throws OAuthException;
}
