package service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PsnAuthenticationService implements AuthenticationService {

    private static final String BUNGIE_SIGNIN_URI = "https://www.bungie.net/en/User/SignIn/Psnid";
    private static final String PSN_OAUTH_URI = "https://auth.api.sonyentertainmentnetwork.com/login.do";

    @Override
    public AuthenticationContext authenticate() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

        // get bungie jsessionid
        HttpGet request = new HttpGet(BUNGIE_SIGNIN_URI);
        HttpResponse response = httpClient.execute(request, localContext);

        // post psn jsessionid and auth location
        HttpPost post = new HttpPost(PSN_OAUTH_URI);
        List<NameValuePair> credentials = new ArrayList<>();
        credentials.add(new BasicNameValuePair("j_username", "<PSN-EMAIL>"));
        credentials.add(new BasicNameValuePair("j_password", "<PSN-PASSWORD>"));
        post.setEntity(new UrlEncodedFormEntity(credentials));

        HttpResponse postResponse = httpClient.execute(post, localContext);

        String url = postResponse.getFirstHeader("location").getValue();
        int x = url.lastIndexOf('=');
        // required before bungie will set cookies
        String state = url.substring(x + 1); // TODO: fix

        // get psn grant code
        HttpGet get = new HttpGet(url);
        HttpParams params = new BasicHttpParams();
        params.setParameter("http.protocol.handle-redirects", false);
        get.setParams(params);
        HttpResponse getResponse = httpClient.execute(get, localContext);
        String grantCode = getResponse.getFirstHeader("X-NP-GRANT-CODE").getValue();

        // log in to bungie with grant code
        HttpGet signIn = new HttpGet(BUNGIE_SIGNIN_URI + "?code=" + grantCode + "&state=" + state);
        HttpResponse signInResponse = httpClient.execute(signIn, localContext);

        return new AuthenticationContext(localContext, getCookieByName("bungled", cookieStore));
    }

    private String getCookieByName(String name, CookieStore cookieStore) {
        for(Cookie cookie : cookieStore.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        throw new RuntimeException("Could not find cookie with name: " + name);
    }
}