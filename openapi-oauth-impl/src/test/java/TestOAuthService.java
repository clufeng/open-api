import com.yonyou.mcloud.Locator;
import com.yonyou.openapi.oauth.OAuthException;
import com.yonyou.openapi.oauth.OAuthServicePrx;
import com.yonyou.openapi.oauth.OAuthToken;
import com.yonyou.openapi.oauth.OAuthUrl;

/**
 * Created by duduchao on 16/3/4
 */
public class TestOAuthService {

    public static void main(String[] args) throws OAuthException {
        OAuthServicePrx oAuthService = Locator.lookup(OAuthServicePrx.class);
        for (int i = 0; i < 10; i++) {
            OAuthUrl url = new OAuthUrl();
            url.setScheme("https");
            url.setMethod("POST");
            url.setClientId("0001");
            url.setClientSecret("d8346ea2601743ed");
            url.setGrantType("clientcredentials");
            OAuthToken token = oAuthService.authorize(url);
            System.out.println(token.getAccessToken());
            oAuthService.validateToken(token.getAccessToken());
        }

    }

}
