package com.yonyou.openapi.oauth.model;

/**
 * Created by hubo on 2016/2/24
 */
public class ClientEntity {

    private String clientId;

    private String clientSecret;

    private int type;

    private int scope;

    private int grantType;

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

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public int getGrantType() {
        return grantType;
    }

    public void setGrantType(int grantType) {
        this.grantType = grantType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
