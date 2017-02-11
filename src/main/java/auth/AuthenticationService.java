package auth;

import java.io.IOException;
import java.net.URISyntaxException;

public interface AuthenticationService {

    AuthenticationContext authenticate() throws IOException;

}