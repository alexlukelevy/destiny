package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Bungle {

    public static final String BUNGIE_SIGNIN_URI = "https://www.bungie.net/en/User/SignIn/Psnid";
    public static final String PSN_OAUTH_URI = "https://auth.api.sonyentertainmentnetwork.com/login.do";

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

        // get bungie jsessionid
        HttpGet request = new HttpGet(BUNGIE_SIGNIN_URI);
        HttpResponse response = httpClient.execute(request, localContext);
        System.out.println(cookieStore.getCookies().get(0).getName() + ": " + cookieStore.getCookies().get(0).getValue());

        // post psn jsessionid and auth location
        HttpPost post = new HttpPost(PSN_OAUTH_URI);
        List<NameValuePair> credentials = new ArrayList<NameValuePair>();
        credentials.add(new BasicNameValuePair("j_username", "<USERNAME>"));
        credentials.add(new BasicNameValuePair("j_password", "<PASSWORD>"));
        post.setEntity(new UrlEncodedFormEntity(credentials));

        HttpResponse postResponse = httpClient.execute(post, localContext);
        System.out.println(cookieStore.getCookies().get(0).getName() + ": " + cookieStore.getCookies().get(0).getValue());

        String url = postResponse.getFirstHeader("location").getValue();
        System.out.println("Location: " + url);

        // get psn grant code
        HttpGet get = new HttpGet(url);
        HttpParams params = new BasicHttpParams();
        params.setParameter("http.protocol.handle-redirects", false);
        get.setParams(params);
        HttpResponse getResponse = httpClient.execute(get, localContext);
        String grantCode = getResponse.getFirstHeader("X-NP-GRANT-CODE").getValue();
        System.out.println("Granty code: " + grantCode);

        // log in to bungie with grant code
        HttpGet signIn = new HttpGet(BUNGIE_SIGNIN_URI + "?code=" + grantCode);
        HttpResponse signInResponse = httpClient.execute(signIn, localContext);

        // item test
        String items = "https://www.bungie.net/Platform/Destiny/2/Account/4611686018437367162/Character/2305843009227473964/Inventory/?definitions=false";
        HttpGet itemGet = new HttpGet(items);
        itemGet.setHeader("X-API-Key", "<API-KEY>");
        itemGet.setHeader("x-csrf", cookieStore.getCookies().get(2).getValue());
        String json = getContent(httpClient.execute(itemGet, localContext));

        System.out.println(json);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        System.out.printf("");

    }

    private static String getContent(HttpResponse response) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));

        StringBuilder content = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            content.append(line);
        }
        return content.toString();
    }
}