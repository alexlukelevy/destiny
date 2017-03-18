package uk.co.alexlevy.auth;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class PsnAuthenticationService implements AuthenticationService {

    private static final String BUNGIE_SIGNIN_URI = "https://www.bungie.net/en/User/SignIn/Psnid";
    private static final String PSN_OAUTH_URI = "https://auth.api.sonyentertainmentnetwork.com/login.do";

    private String psnId;
    private String psnPass;

    public PsnAuthenticationService(String psnId, String psnPass) {
        this.psnId = psnId;
        this.psnPass = psnPass;
    }

    @Override
    public AuthenticationContext authenticate() throws IOException {
        // cookieStore and localContext maintain the state required to
        // log into bungie API
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

        // get bungie jsessionid
        obtainBungieJsessionid(httpClient, localContext);

        // post psn jsessionid and auth location
        HttpResponse psnLogin = psnLogin(httpClient, localContext);
        String url = psnLogin.getFirstHeader("location").getValue();

        // get psn state parameter
        String state = getStateParameter(url);

        // get psn grant code
        String grantCode = psnGrantCode(httpClient, localContext, url);

        // log in to bungie with grant code
        bungieLogin(httpClient, localContext, grantCode, state);

        return new AuthenticationContext(localContext, getCookieByName("bungled", cookieStore));
    }

    private void bungieLogin(CloseableHttpClient httpClient, HttpClientContext localContext, String grantCode, String state) throws IOException {
        HttpGet signIn = new HttpGet(BUNGIE_SIGNIN_URI + "?code=" + grantCode + "&state=" + state);
        httpClient.execute(signIn, localContext);
    }

    private void obtainBungieJsessionid(CloseableHttpClient httpClient, HttpClientContext localContext) throws IOException {
        HttpGet request = new HttpGet(BUNGIE_SIGNIN_URI);
        httpClient.execute(request, localContext);
    }

    private String psnGrantCode(CloseableHttpClient httpClient, HttpClientContext localContext, String url) throws IOException {
        HttpGet get = new HttpGet(url);
        HttpParams params = new BasicHttpParams();
        params.setParameter("http.protocol.handle-redirects", false);
        get.setParams(params);
        HttpResponse getResponse = httpClient.execute(get, localContext);
        return getResponse.getFirstHeader("X-NP-GRANT-CODE").getValue();
    }

    private HttpResponse psnLogin(CloseableHttpClient httpClient, HttpClientContext context) throws IOException {
        HttpPost post = new HttpPost(PSN_OAUTH_URI);
        List<NameValuePair> credentials = new ArrayList<>();
        credentials.add(new BasicNameValuePair("j_username", psnId));
        credentials.add(new BasicNameValuePair("j_password", psnPass));
        post.setEntity(new UrlEncodedFormEntity(credentials));

        return httpClient.execute(post, context);
    }

    private String getStateParameter(String url) throws IOException {
        try {
            return new URIBuilder(url).getQueryParams()
                    .stream()
                    .filter(p -> p.getName().equals("state"))
                    .findFirst()
                    .map(NameValuePair::getValue)
                    .get();
        } catch (URISyntaxException e) {
            throw new IOException("Problem finding 'state' query parameter");
        }
    }

    private String getCookieByName(String name, CookieStore cookieStore) {
        for (Cookie cookie : cookieStore.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        throw new RuntimeException("Could not find cookie with name: " + name);
    }
}