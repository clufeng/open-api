package com.yonyou.mcloud.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yonyou.openapi.oauth.OAuthToken;

/**
 * Created by duduchao on 16/3/7
 */
public class OAuthTokenJson {

    @JsonProperty(value = "access_token", index = 1)
    private String accessToken;

    @JsonProperty(value = "token_type", index = 2)
    private String tokenType;

    @JsonProperty(value = "expires_in", index = 3)
    private int expiresIn;

    @JsonProperty(value = "refresh_token", index = 4, required = false)
    private String refreshToken;

    @JsonProperty(value = "scope", index = 5)
    private String scope;

    public OAuthTokenJson(String accessToken, int expiresIn, String refreshToken, String scope, String tokenType) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.scope = scope;
        this.tokenType = tokenType;
    }

    public OAuthTokenJson() {
    }

    public OAuthTokenJson(OAuthToken token) {
        this.accessToken = token.getAccessToken();
        this.expiresIn = token.getExpiresIn();
        this.refreshToken = token.getRefreshToken();
        this.scope = token.getScope();
        this.tokenType = token.getTokenType();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
