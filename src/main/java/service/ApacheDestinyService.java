package service;

import auth.AuthenticationContext;
import auth.AuthenticationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class ApacheDestinyService implements DestinyService {

    private static final String BASE_URL = "http://www.bungie.net/Platform/Destiny/";

    private final AuthenticationService authenticationService;
    private final CloseableHttpClient httpClient;

    public ApacheDestinyService(AuthenticationService authenticationService, CloseableHttpClient httpClient) {
        this.authenticationService = authenticationService;
        this.httpClient = httpClient;
    }

    @Override
    public JsonNode getMembership(int membershipTypeId, String username) throws IOException {
        String serviceUrl = "SearchDestinyPlayer/" + membershipTypeId + "/" + username;
        return getJson(serviceUrl);
    }

    @Override
    public JsonNode getCharacters(int membershipTypeId, long membershipId) throws IOException {
        String serviceUrl = membershipTypeId + "/Account/" + membershipId + "/Summary";
        JsonNode root = getJson(serviceUrl);
        return root.findValue("characters");
    }

    @Override
    public JsonNode getInventory(int membershipTypeId, long membershipId, long characterId) throws IOException {
        String serviceUrl = membershipTypeId + "/Account/" + membershipId + "/Character/" + characterId + "/Inventory/?definitions=true";
        return getJson(serviceUrl);
    }

    private JsonNode getJson(String url) throws IOException {
        HttpResponse response = executeGet(url);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(response.getEntity().getContent());
    }

    private HttpResponse executeGet(String url) throws IOException {
        AuthenticationContext cxt = this.authenticationService.authenticate();
        HttpGet inventory = new HttpGet(BASE_URL + url);
        inventory.setHeader("X-API-Key", cxt.apiKey);
        inventory.setHeader("x-csrf", cxt.xsrf);
        return this.httpClient.execute(inventory, cxt.context);
    }
}