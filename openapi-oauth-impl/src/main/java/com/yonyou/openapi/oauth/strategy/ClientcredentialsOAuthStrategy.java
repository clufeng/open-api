package com.yonyou.openapi.oauth.strategy;

import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.OAuthUrl;
import org.springframework.stereotype.Component;

/**
 * Created by hubo on 2016/2/24
 */
@Component("ClientcredentialsOAuthStrategy")
public class ClientcredentialsOAuthStrategy extends AbstractOAuthStrategy {

    @Override
    public void authorize0(OAuthUrl url) throws OAuthException {

    }
}
