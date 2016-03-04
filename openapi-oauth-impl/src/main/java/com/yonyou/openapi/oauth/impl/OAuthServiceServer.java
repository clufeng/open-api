package com.yonyou.openapi.oauth.impl;

import Ice.Object;
import com.yonyou.mcloud.service.AbstractService;

/**
 * Created by duduchao on 16/3/4
 */
public class OAuthServiceServer extends AbstractService {
    @Override
    public Object createServiceObject() {
        return new OAuthServiceImpl();
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
