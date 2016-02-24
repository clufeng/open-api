package com.yonyou.openapi.oauth.strategy;

import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.OAuthToken;
import com.yonyou.openapi.oauth.OAuthUrl;

/**
 * Created by hubo on 2016/2/24
 */
public interface IOAuthStrategy {
    OAuthToken authorize(OAuthUrl url) throws OAuthException;
}
