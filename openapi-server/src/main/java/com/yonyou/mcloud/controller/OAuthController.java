package com.yonyou.mcloud.controller;

import com.yonyou.mcloud.Locator;
import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.OAuthServicePrx;
import com.yonyou.openapi.oauth.OAuthToken;
import com.yonyou.openapi.oauth.OAuthUrl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by duduchao on 16/3/4
 */

@RestController
@RequestMapping("/oauth2")
public class OAuthController extends BasicController {

    @RequestMapping(value = "/access_token", method = RequestMethod.POST)
    public Object applyTokey(
            @RequestParam(value = "client_id", required = true) String clientId,
            @RequestParam(value = "client_secret", required = true) String clientSecret,
            @RequestParam(value = "grant_type", required = true) String grantType,
            @RequestParam(value = "scope", required = false) String scope,
            HttpServletRequest request) {

        OAuthServicePrx oAuthService = Locator.lookup(OAuthServicePrx.class);
        OAuthUrl url = new OAuthUrl();
        url.setScheme(request.getScheme()+"s");
        url.setMethod(request.getMethod());
        url.setClientId(clientId);
        url.setClientSecret(clientSecret);
        url.setGrantType(grantType);
        OAuthToken token;
        try {
            token = oAuthService.authorize(url);
        } catch (OAuthException e) {
            return error(String.valueOf(e.code));
        }
        return success(token);

    }

}
