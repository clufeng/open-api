package com.yonyou.mcloud.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * OAuth 请求参数
 * Created by hubo on 2016/3/7.
 */
public class OAuthRequestArgs {

    private String clientId;
    private String clientSecret;
    private String grantType;
    private String scope;
    private String refreshToken;
    private String username;
    private String password;
    private String code;
    private String redirectUri;

    public OAuthRequestArgs() {
    }

    public OAuthRequestArgs(String clientId, String clientSecret, String grantType, String scope, String refreshToken, String username, String password, String code, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.scope = scope;
        this.refreshToken = refreshToken;
        this.username = username;
        this.password = password;
        this.code = code;
        this.redirectUri = redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirect) {
        this.redirectUri = redirect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OAuthRequestArgs that = (OAuthRequestArgs) o;

        if (!clientId.equals(that.clientId)) return false;
        if (!clientSecret.equals(that.clientSecret)) return false;
        if (!grantType.equals(that.grantType)) return false;
        if (!scope.equals(that.scope)) return false;
        if (!refreshToken.equals(that.refreshToken)) return false;
        if (!username.equals(that.username)) return false;
        if (!password.equals(that.password)) return false;
        if (!code.equals(that.code)) return false;
        return redirectUri.equals(that.redirectUri);

    }

    @Override
    public int hashCode() {
        int result = clientId.hashCode();
        result = 31 * result + clientSecret.hashCode();
        result = 31 * result + grantType.hashCode();
        result = 31 * result + scope.hashCode();
        result = 31 * result + refreshToken.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + redirectUri.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OAuthRequestArgs{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", grantType='" + grantType + '\'' +
                ", scope='" + scope + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", code='" + code + '\'' +
                ", redirect='" + redirectUri + '\'' +
                '}';
    }
}
