package com.yonyou.openapi.oauth.service;

import com.yonyou.openapi.oauth.model.ClientEntity;
import org.springframework.stereotype.Service;

/**
 * Created by hubo on 2016/2/24
 */
@Service
public class ClientService {

    public ClientEntity getClientInfo(String clientId) {
        ClientEntity entity = new ClientEntity();
        entity.setClientId("0001");
        entity.setClientSecret("d8346ea2601743ed");
        entity.setScope(21);
        entity.setGrantType(21);
        entity.setType(21);
        return entity;
    }

}
