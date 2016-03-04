package com.yonyou.mcloud.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射的工具类。
 * 
 * @author wangshqb
 * 
 */
public class ReflectUtils {
    /**
     * 得到指定类型的指定位置的泛型实参。
     * 
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> findParameterizedType(Class<?> clazz) {
        Type parameterizedType = clazz.getGenericSuperclass();
        // 泛型在父类上
        if (!(parameterizedType instanceof ParameterizedType)) {
            parameterizedType = clazz.getSuperclass().getGenericSuperclass();
        }
        if (!(parameterizedType instanceof ParameterizedType)) {
            return null;
        }
        Type[] actualTypeArguments =
                ((ParameterizedType) parameterizedType)
                        .getActualTypeArguments();
        if (actualTypeArguments == null || actualTypeArguments.length == 0) {
            return null;
        }
        return (Class<T>) actualTypeArguments[0];
    }
}
