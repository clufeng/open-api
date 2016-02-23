package com.yonyou.openapi.oauth.impl;

import Ice.Current;
import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.OAuthToken;
import com.yonyou.openapi.oauth.OAuthUrl;
import com.yonyou.openapi.oauth._OAuthServiceDisp;
import org.apache.commons.lang3.StringUtils;

import static com.yonyou.openapi.oauth.impl.OAuthErrorCode.*;

/**
 * Created by hubo on 2016/2/23
 */
public class OAuthServiceImpl extends _OAuthServiceDisp {

    public OAuthToken authorize(OAuthUrl url, Current __current) throws OAuthException {

        if(url == null) {
            throw new OAuthException(OAEC_SYSTEM_ERROR);
        }

        // 判断是否是加密协议
        if (!"https".equals(url.getScheme())) {
            // 必须是加密的协议
            throw new OAuthException(OAEC_MUST_HTTPS_SCHEME);
        }

        // 判断GrantType是否为null
        if(StringUtils.isEmpty(url.getGrantType())) {
            throw new OAuthException(OAEC_LACK_PARAM);
        }

        if (!"POST".equals(url.getMethod())) {
            // 必须是POST方法
            throw new OAuthException(OAEC_MUST_POST_METHOD);
        }

//        int grantType = OAuth2ConstSet.strToGrantType(grantTypeStr);

        if (StringUtils.isEmpty(url.getClientId())) {
            // 缺少client_id
            throw new OAuthException(OAEC_LACK_CLIEND_ID);
        }
//        long clientId = Long.parseLong(clientIdStr, 16);

/*        ClientEntity clientInfo = clientManager.getClientInfo(clientId);
        if (clientInfo == null) {
            // 无法获取客户端信息
            setOAuth2ErrorResponse(httpResponse,
                    OAuth2ErrorCode.OAEC_CANT_GET_CLIENT_INFO,
                    httpRequest.getRequestURI());
            return;

        }
        // 此第三方应用是否拥有此认证方法的权限
        if ((clientInfo.getGrantType() & grantType) == 0) {
            // 没有权限
            setOAuth2ErrorResponse(httpResponse,
                    OAuth2ErrorCode.OAEC_GRANT_TYPE_DENIED,
                    httpRequest.getRequestURI());
            return;
        }
        String clientSecret =
                httpRequest.getParameter(OAuth2ConstSet.CLIENT_SECRET);
        // 验证第三方应用的密钥
        if (!clientSecret.equals(clientInfo.getClientSecret())) {
            // 密钥验证失败
            setOAuth2ErrorResponse(httpResponse,
                    OAuth2ErrorCode.OAEC_INVALID_CLIENT_SECRET,
                    httpRequest.getRequestURI());
            return;
        }*/

        return null;
    }

    public void validateToken(String accessToken, Current __current) throws OAuthException {

    }

    public String createCode(OAuthUrl url, Current __current) throws OAuthException {

        if(StringUtils.isEmpty(url.getResponseType())) {
            throw new OAuthException(OAEC_LACK_PARAM);
        }


        return null;
    }

}
