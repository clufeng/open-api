[["java:package:com.yonyou.openapi"]]

module oauth {

	struct OAuthUrl {
		string scheme;
		string host;
		string method;
		string uri;
		string responseType;
		string grantType;
		string clientId;
		string clientSecret;
		string redirectUri;
		string state;
		string forceLogin;
		string code;
		string scope;
		string accessToken;
		string refreshToken;
		string username;
		string password;
	};

	struct OAuthToken {
		string accessToken;
		string tokenType;
		int expiresIn;
		string refreshToken;
		string scope;
	};

	exception OAuthException {
		int code;
	};

	interface OAuthService {
		OAuthToken authorize(OAuthUrl url) throws OAuthException;
		void validateToken(string accessToken) throws OAuthException;
		string createCode(OAuthUrl url) throws OAuthException;
	};

};