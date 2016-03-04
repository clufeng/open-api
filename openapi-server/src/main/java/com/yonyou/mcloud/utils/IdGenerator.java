package com.yonyou.mcloud.utils;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/**
 * ID 生成器
 * Created by hubo on 2016/2/29
 */
public class IdGenerator implements IdentifierGenerator, Configurable {

    public static final String SYSTEM_ID = "0001";
    /**
     * 系统管理模块编码
     */
    public static final String SM_MODULE_CODE = "SM";

    public static String generateId(String moduleCode) {
        if(moduleCode == null || moduleCode.length() != 2) {
            throw new IllegalArgumentException("module code illegal !");
        }
        return moduleCode + SequenceGenerator.getInstance().next();
    }

    public static String generateId() {
        return generateId(SM_MODULE_CODE);
    }

    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        return generateId(SM_MODULE_CODE);
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {

    }
}
