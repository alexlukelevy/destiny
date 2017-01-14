package auth;

import org.apache.http.client.protocol.HttpClientContext;

public class AuthenticationContext {

    public HttpClientContext context;
    public String xsrf;
    public String apiKey;

    public AuthenticationContext(HttpClientContext context, String xsrf) {
        this.context = context;
        this.xsrf = xsrf;
        this.apiKey = "57c5ff5864634503a0340ffdfbeb20c0";
    }
}