package com.yonyou.openapi.oauth.strategy;

import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.OAuthUrl;
import com.yonyou.openapi.oauth.impl.OAuthErrorCode;
import org.springframework.stereotype.Component;

/**
 * Created by duduchao on 16/3/4
 */
@Component("RefreshTokenOAuthStrategy")
public class RefreshTokenOAuthStrategy extends AbstractOAuthStrategy {

    @Override
    public void authorize0(OAuthUrl url) throws OAuthException {

        if(!getTokenService().vaildateRefreshToke(url.getRefreshToken())) {
            throw new OAuthException(OAuthErrorCode.OAEC_INVALID_REFRESH_TOKEN);
        }

    }
}
