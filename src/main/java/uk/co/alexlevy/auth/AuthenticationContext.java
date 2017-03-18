package uk.co.alexlevy.auth;

import org.apache.http.client.protocol.HttpClientContext;

public class AuthenticationContext {

    public HttpClientContext context;
    public String xsrf;
    public String apiKey;

    public AuthenticationContext(HttpClientContext context, String xsrf) {
        this.context = context;
        this.xsrf = xsrf;
        // API key left in intentionally since they are freely granted by Bungie
        // and it significantly reduces the barrier to entry for a user of the app
        this.apiKey = "06aa6b97e6d041a2a4db3af84847b026";
    }
}