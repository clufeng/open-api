package com.yonyou.openapi.oauth.impl;

import Ice.Current;
import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.OAuthToken;
import com.yonyou.openapi.oauth.OAuthUrl;
import com.yonyou.openapi.oauth._OAuthServiceDisp;
import com.yonyou.openapi.oauth.model.ClientEntity;
import com.yonyou.openapi.oauth.service.ClientService;
import com.yonyou.openapi.oauth.service.CodeService;
import com.yonyou.openapi.oauth.service.TokenService;
import com.yonyou.openapi.oauth.strategy.IOAuthStrategy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

import static com.yonyou.openapi.oauth.impl.OAuthErrorCode.*;

/**
 *
 * Created by hubo on 2016/2/23
 */
@Component("OAuthServiceImpl")
public class OAuthServiceImpl extends _OAuthServiceDisp {

    private static Logger logger = LoggerFactory.getLogger(OAuthServiceImpl.class);

    @Resource(name = "oauthStrategyMap")
    protected HashMap<String, IOAuthStrategy> strategyMap;

    @Autowired
    private ClientService clientService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CodeService codeService;

    public OAuthToken authorize(OAuthUrl url, Current __current) throws OAuthException {

        if(url == null) {
            throw new OAuthException(OAEC_SYSTEM_ERROR);
        }

        // 判断是否是加密协议
        if (!"https".equals(url.getScheme())) {
            // 必须是加密的协议
            throw new OAuthException(OAEC_MUST_HTTPS_SCHEME);
        }

        if (!"POST".equals(url.getMethod())) {
            // 必须是POST方法
            throw new OAuthException(OAEC_MUST_POST_METHOD);
        }

        // 判断GrantType是否为null
        if(StringUtils.isEmpty(url.getGrantType())) {
            throw new OAuthException(OAEC_LACK_PARAM);
        }

        if (StringUtils.isEmpty(url.getClientId())) {
            // 缺少client_id
            throw new OAuthException(OAEC_LACK_CLIEND_ID);
        }

        ClientEntity clientInfo = clientService.getClientInfo(url.getClientId());

        if (clientInfo == null) {
            // 无法获取客户端信息
            throw new OAuthException(OAEC_CANT_GET_CLIENT_INFO);
        }

        // 此第三方应用是否拥有此认证方法的权限
//        if ((clientInfo.getGrantType() & grantType) == 0) {
//            // 没有权限
//            throw new OAuthException(OAEC_GRANT_TYPE_DENIED);
//        }

        // 验证第三方应用的密钥
        if (!url.getClientSecret().equals(clientInfo.getClientSecret())) {
            // 密钥验证失败
            throw new OAuthException(OAEC_INVALID_CLIENT_SECRET);
        }

        if(!strategyMap.containsKey(url.getGrantType())) {
            throw new OAuthException(OAEC_UNSUPPORTED_GRANT_TYPE);
        }

        logger.info("authorize : {}", url);

        return strategyMap.get(url.getGrantType()).authorize(url);

    }

    public void validateToken(String accessToken, Current __current) throws OAuthException {

        if(StringUtils.isEmpty(accessToken)) {
            throw new OAuthException(OAEC_LACK_PARAM);
        }

        if(!tokenService.vaildateToke(accessToken)) {
            throw new OAuthException(OAEC_INVALID_ACCESS_TOKEN);
        }

        logger.info("validateToken success : token[{}]", accessToken);

    }

    public String createCode(OAuthUrl url, Current __current) throws OAuthException {

        if(url == null) {
            throw new OAuthException(OAEC_SYSTEM_ERROR);
        }

        // 判断是否是加密协议
        if (!"https".equals(url.getScheme())) {
            // 必须是加密的协议
            throw new OAuthException(OAEC_MUST_HTTPS_SCHEME);
        }

        if (StringUtils.isEmpty(url.getClientId())) {
            // 缺少client_id
            throw new OAuthException(OAEC_LACK_CLIEND_ID);
        }

        ClientEntity clientInfo = clientService.getClientInfo(url.getClientId());

        if (clientInfo == null) {
            // 无法获取客户端信息
            throw new OAuthException(OAEC_CANT_GET_CLIENT_INFO);
        }

        if (StringUtils.isEmpty(url.getRedirectUri())) {
            // 缺少redirect_uri
            throw new OAuthException(OAEC_LACK_REDIRECT_URI);
        }

        if(StringUtils.isEmpty(url.getResponseType())) {
            throw new OAuthException(OAEC_LACK_PARAM);
        }

        if(!url.getResponseType().equals("code")) {
            throw new OAuthException(OAEC_UNSUPPORTED_RESPONSE_TYPE);
        }

        logger.info("createCode : {}", url);

        return codeService.createCode(url.getClientId(), url.getRedirectUri());
    }

}
