package com.yonyou.mcloud.controller;

import com.yonyou.mcloud.Locator;
import com.yonyou.mcloud.pojo.OAuthTokenJson;
import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.OAuthServicePrx;
import com.yonyou.openapi.oauth.OAuthToken;
import com.yonyou.openapi.oauth.OAuthUrl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by duduchao on 16/3/4
 */

@RestController
@RequestMapping("/oauth2")
public class OAuthController extends BasicController {

    @RequestMapping(value = "/access_token", method = RequestMethod.POST)
    public Object applyToken(
            @RequestParam(value = "client_id", required = true) String clientId,
            @RequestParam(value = "client_secret", required = true) String clientSecret,
            @RequestParam(value = "grant_type", required = true) String grantType,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(value = "refresh_token", required = false) String refreshToken,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "code", required = false) String code,
            HttpServletRequest request) {

        OAuthServicePrx oAuthService = Locator.lookup(OAuthServicePrx.class);
        OAuthUrl url = new OAuthUrl();
        url.setScheme(request.getScheme()+"s");
        url.setMethod(request.getMethod());
        url.setClientId(clientId);
        url.setClientSecret(clientSecret);
        url.setGrantType(grantType);
        url.setRefreshToken(refreshToken);
        url.setUsername(username);
        url.setPassword(password);
        url.setScope(scope);
        url.setCode(code);
        OAuthToken token;
        try {
            token = oAuthService.authorize(url);
        } catch (OAuthException e) {
            return error(String.valueOf(e.code));
        }
        return success(new OAuthTokenJson(token));

    }


    @RequestMapping(value = "/access_token", method = RequestMethod.GET)
    public Object generateCode(
            @RequestParam(value = "client_id", required = true) String clientId,
            @RequestParam(value = "response_type", required = true) String responseType,
            @RequestParam(value = "redirect_uri", required = true) String redirectUri,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(value = "state", required = false) String state,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        OAuthServicePrx oAuthService = Locator.lookup(OAuthServicePrx.class);

        OAuthUrl url = new OAuthUrl();
        url.setScheme(request.getScheme()+"s");
        url.setMethod(request.getMethod());
        url.setClientId(clientId);
        url.setResponseType(responseType);
        url.setRedirectUri(redirectUri);
        url.setState(state);
        url.setScope(scope);

        String code;
        try {
            code = oAuthService.createCode(url);
        } catch (OAuthException e) {
            return error(String.valueOf(e.code));
        }

        StringBuilder redirectUrl = new StringBuilder();
        // 如果拿到了授权码
        if (code != null) {
            redirectUrl.append(redirectUri);
            if (redirectUrl.indexOf("?") >= 0) {
                redirectUrl.append("&");
            }
            else {
                redirectUrl.append("?");
            }
            redirectUrl.append("code").append("=")
                    .append(code);
        }

        if (state != null) {
            redirectUrl.append("&").append("state")
                    .append("=").append(URLEncoder.encode(state,
                    "UTF-8"));
        }

        response.sendRedirect(redirectUrl.toString());

        return null;
    }

}
