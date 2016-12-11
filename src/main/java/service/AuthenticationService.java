package service;

import org.apache.http.client.protocol.HttpClientContext;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationContext authenticate() throws IOException;

    public static class AuthenticationContext {
        public HttpClientContext context;
        public String xsrf;

        public AuthenticationContext(HttpClientContext context, String xsrf) {
            this.context = context;
            this.xsrf = xsrf;
        }
    }

}