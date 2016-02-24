package com.yonyou.openapi.oauth.strategy;

import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.OAuthToken;
import com.yonyou.openapi.oauth.OAuthUrl;
import com.yonyou.openapi.oauth.service.TokenService;
import com.yonyou.openapi.oauth.service.CodeService;

import static com.yonyou.openapi.oauth.impl.OAuthErrorCode.OAEC_INVALID_CODE;
import static com.yonyou.openapi.oauth.impl.OAuthErrorCode.OAEC_LACK_CODE;

/**
 * Created by hubo on 2016/2/24
 */
public class CodeOAuthStrategy implements IOAuthStrategy {

    private CodeService codeService;

    private TokenService tokenService;

    @Override
    public OAuthToken authorize(OAuthUrl url) throws OAuthException {

        String code = url.getCode();

        if (code == null) {
            // 缺少code
            throw new OAuthException(OAEC_LACK_CODE);
        }

        // 验证授权码
        if ( codeService.validateCode(url.getClientId(), url.getCode())) {
            throw new OAuthException(OAEC_INVALID_CODE);
        }

        // 创建access_token
        return tokenService.createOAuthToken(url.getClientId(), url.getGrantType());

    }
}
