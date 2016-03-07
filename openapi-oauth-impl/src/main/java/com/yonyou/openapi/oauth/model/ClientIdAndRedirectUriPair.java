package com.yonyou.openapi.oauth.model;

import java.io.Serializable;

/**
 *
 * Created by hubo on 2016/3/7.
 */
public class ClientIdAndRedirectUriPair implements Serializable{

    private final String clientId;

    private final String redirectUri;

    public ClientIdAndRedirectUriPair(String clientId, String redirectUri) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

}
