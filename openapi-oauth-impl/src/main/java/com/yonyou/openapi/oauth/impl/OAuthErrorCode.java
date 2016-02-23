package com.yonyou.openapi.oauth.impl;

/**
 * OAuth2.0内部错误码
 * 内部错误码对应的错误描述定义在oauth2-error-description.properties中
 * 内部错误码对应的标准ASCII错误码在oauth2-error.properties中
 * 
 * @author lcxl
 *
 */
public class OAuthErrorCode {
    /**
     * 认证成功，一般不用，http statue code为2XX时就是成功的
     */
    public static final int OAEC_OK = 0x0000;

    /**
     * 系统相关错误
     */
    public static final int OAEC_SYSTEM_ERROR = 0x1000;

    /**
     * OAuth2.0相关错误
     */
    public static final int OAEC_OAUTH2_ERROR = 0x2000;

    /**
     * access_token未找到
     */
    public static final int OAEC_ACCESS_TOKEN_NOT_FOUND = 0x2001;

    /**
     * 无效的Authorization
     */
    public static final int OAEC_INVALID_AUTHORIZATION = 0x2002;

    /**
     * 未知的Authorization类型
     */
    public static final int OAEC_UNKONWN_AUTHORIZATION_TYPE = 0x2003;

    /**
     * 缺少权限
     */
    public static final int OAEC_LACK_SCOPE = 0x2004;

    /**
     * 错误的http方法
     */
    public static final int OAEC_INVALID_HTTP_METHOD = 0x2005;

    /**
     * 必须是https协议
     */
    public static final int OAEC_MUST_HTTPS_SCHEME = 0x2006;

    /**
     * 缺少参数
     */
    public static final int OAEC_LACK_PARAM = 0x2007;

    /**
     * 不支持的认证方法
     */
    public static final int OAEC_UNSUPPORTED_GRANT_TYPE = 0x2008;

    /**
     * 缺少code
     */
    public static final int OAEC_LACK_CODE = 0x2009;

    /**
     * 缺少client_id
     */
    public static final int OAEC_LACK_CLIEND_ID = 0x200A;

    /**
     * 必须是POST方法
     */
    public static final int OAEC_MUST_POST_METHOD = 0x200B;

    /**
     * 授权码验证失败
     */
    public static final int OAEC_INVALID_CODE = 0x200C;

    /**
     * 无效的第三方应用的密钥
     */
    public static final int OAEC_INVALID_CLIENT_SECRET = 0x200D;

    /**
     * 不支持的授权方法
     */
    public static final int OAEC_UNSUPPORTED_RESPONSE_TYPE = 0x200E;

    /**
     * 缺少redirect_uri参数
     */
    public static final int OAEC_LACK_REDIRECT_URI = 0x200F;

    /**
     * 无法获取客户端信息
     */
    public static final int OAEC_CANT_GET_CLIENT_INFO = 0x2010;

    /**
     * 客户端的认证类型不匹配
     */
    public static final int OAEC_GRANT_TYPE_MISMATCH = 0x2011;

    /**
     * 客户端的重定向URL不匹配
     */
    public static final int OAEC_REDIRECT_URI_MISMATCH = 0x2012;

    /**
     * access_token无效，可能已过期，需要重新授权
     */
    public static final int OAEC_INVALID_ACCESS_TOKEN = 0x2013;

    /**
     * 当前第三方应用权限不足，不能访问此URL
     */
    public static final int OAEC_INSUFFCIENT_SCOPE = 0x2014;

    /**
     * 用户验证失败
     */
    public static final int OAEC_USER_VALIDATE_FAILED = 0x2015;

    /**
     * 第三方应用没有此认证方法的权限
     */
    public static final int OAEC_GRANT_TYPE_DENIED = 0x2016;

    /**
     * client_id格式错误
     */
    public static final int OAEC_INVALID_CLIENT_ID = 0x2017;

    /**
     * 缺少access_token参数
     */
    public static final int OAEC_LACK_ACCESS_TOKEN = 0x2018;
}
