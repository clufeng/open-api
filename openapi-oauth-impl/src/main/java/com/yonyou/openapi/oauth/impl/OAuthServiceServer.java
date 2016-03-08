package com.yonyou.openapi.oauth.impl;

import Ice.Object;
import com.yonyou.mcloud.service.AbstractService;
import com.yonyou.mcloud.service.util.SpringUtil;

/**
 * Created by duduchao on 16/3/4
 */
public class OAuthServiceServer extends AbstractService {
    @Override
    public Object createServiceObject() {
        return (Ice.Object)SpringUtil.getBean("OAuthServiceImpl");
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
