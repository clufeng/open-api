package com.yonyou.openapi.oauth.strategy;

import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.OAuthUrl;
import com.yonyou.openapi.oauth.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.yonyou.openapi.oauth.impl.OAuthErrorCode.*;

/**
 * Created by hubo on 2016/2/24
 */
@Component("PasswordOAuthStrategy")
public class PasswordOAuthStrategy extends AbstractOAuthStrategy {

    @Autowired
    private UserService userService;

    @Override
    public void authorize0(OAuthUrl url) throws OAuthException {

        if(StringUtils.isEmpty(url.getUsername()) || StringUtils.isEmpty(url.getPassword())) {
            throw new OAuthException(OAEC_LACK_PARAM);
        }

        if(!userService.validateUser(url.getUsername(), url.getPassword())) {
            throw new OAuthException(OAEC_USER_VALIDATE_FAILED);
        }

    }
}
