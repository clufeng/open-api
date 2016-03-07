package com.yonyou.openapi.oauth.strategy;

import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.OAuthUrl;
import com.yonyou.openapi.oauth.service.CodeService;
import org.apache.commons.lang3.StringUtils;

import static com.yonyou.openapi.oauth.impl.OAuthErrorCode.OAEC_LACK_CODE;

/**
 * Created by hubo on 2016/2/24
 */
public class CodeOAuthStrategy extends AbstractOAuthStrategy {

    private CodeService codeService = new CodeService();

    @Override
    public void authorize0(OAuthUrl url) throws OAuthException {

        if (StringUtils.isEmpty(url.getCode())) {
            // 缺少code
            throw new OAuthException(OAEC_LACK_CODE);
        }

        // 验证授权码
        codeService.validateCode(url.getClientId(), url.getCode(), url.getRedirectUri());
    }
}
