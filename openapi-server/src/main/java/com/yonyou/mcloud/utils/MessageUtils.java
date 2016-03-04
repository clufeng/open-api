package com.yonyou.mcloud.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 消息处理工具类。
 * 
 * @author wangshqb
 * 
 */
@Component
public final class MessageUtils implements ApplicationContextAware {
    /**
     * 消息资源
     */
    private static ResourceBundleMessageSource messageSource;

    /**
     * Try to resolve the message. Return empty message if no message was
     * found.
     * 
     * @param code the code to lookup up
     * @return the resolved message if the lookup was successful;
     *         otherwise the empty message
     */
    public static String getMessage(String code) {
        return messageSource.getMessage(code, null, "", Locale.getDefault());
    }

    /**
     * Try to resolve the message. Return default message if no message was
     * found.
     * 
     * @param code the code to lookup up
     * @param defaultMessage String to return if the lookup fails
     * @return the resolved message if the lookup was successful;
     *         otherwise the default message passed as a parameter
     */
    public static String getMessage(String code, String defaultMessage) {
        return messageSource.getMessage(code, null, defaultMessage, Locale.getDefault());
    }

    /**
     * Try to resolve the message. Return empty message if no message was
     * found.
     * 
     * @param code the code to lookup up.
     * @param args array of arguments that will be filled in for params within
     *            the message (params look like "{0}", "{1,date}", "{2,time}"
     *            within a message), or {@code null} if none.
     * @return the resolved message if the lookup was successful;
     *         otherwise the empty message
     */
    public static String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, "", Locale.getDefault());
    }

    /**
     * Try to resolve the message. Return default message if no message was
     * found.
     * 
     * @param code the code to lookup up.
     * @param args array of arguments that will be filled in for params within
     *            the message (params look like "{0}", "{1,date}", "{2,time}"
     *            within a message), or {@code null} if none.
     * @param defaultMessage String to return if the lookup fails
     * @param locale the Locale in which to do the lookup
     * @return the resolved message if the lookup was successful;
     *         otherwise the default message passed as a parameter
     */
    public static String getMessage(String code, Object[] args,
            String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        messageSource = (ResourceBundleMessageSource) applicationContext.getBean("messageSource");
    }
}
