package uk.co.alexlevy.auth;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationContext authenticate() throws IOException;

}