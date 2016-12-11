package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class PsnDestinyApi implements DestinyApi {

    private AuthenticationService authenticationService;
    private CloseableHttpClient httpClient;

    public PsnDestinyApi(AuthenticationService authenticationService, CloseableHttpClient httpClient) {
        this.authenticationService = authenticationService;
        this.httpClient = httpClient;
    }

    @Override
    public JsonNode getInventory(long membershipId, long characterId) throws IOException {
        String url = "https://www.bungie.net/Platform/Destiny/2/Account/" + membershipId + "/Character/" + characterId + "/Inventory/?definitions=true";
        return getJson(executeGet(url));
    }

    private HttpResponse executeGet(String url) throws IOException {
        AuthenticationService.AuthenticationContext cxt = this.authenticationService.authenticate();
        HttpGet inventory = new HttpGet(url);
        inventory.setHeader("X-API-Key", "<API-KEY>");
        inventory.setHeader("x-csrf", cxt.xsrf);
        return this.httpClient.execute(inventory, cxt.context);
    }

    private static JsonNode getJson(HttpResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(response.getEntity().getContent());
    }
}