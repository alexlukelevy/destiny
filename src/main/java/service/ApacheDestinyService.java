package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ApacheDestinyService implements DestinyService {

    private final DefaultHttpClient httpClient;
    private final String baseUrl;
    private final Header authHeader;

    public ApacheDestinyService(String baseUrl, String apiKey) {
        this.httpClient = new DefaultHttpClient();
        this.baseUrl = baseUrl;
        this.authHeader = new BasicHeader("X-API-Key", apiKey);
    }

    // TODO: Create method with String input that returns a specific element

    @Override
    public String getAccountSummary(int membershipTypeId, long membershipId) throws IOException {
        String serviceUrl = "/" + membershipTypeId + "/Account/" + membershipId + "/Summary";
        return getJson(serviceUrl);
    }

    @Override
    public String getCharacterSummary(int membershipTypeId, long membershipId, long characterId) throws IOException {
        String serviceUrl = "/" + membershipTypeId + "/Account/" + membershipId + "/Character/" + characterId;

        return getJson(serviceUrl);
    }

    @Override
    public long getMembershipId(int membershipTypeId, String username) throws IOException {
        String serviceUrl = "/SearchDestinyPlayer/" + membershipTypeId + "/" + username;
        String json = getJson(serviceUrl);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        return root.findValue("membershipId").asLong();
    }

    @Override
    public long getCharacterId(int membershipTypeId, long membershipId, int classTypeId) throws IOException {
        String json = getAccountSummary(membershipTypeId, membershipId);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode characters = root.findValue("characters");

        for(JsonNode character : characters) {
            if(character.findValue("classType").asInt() == classTypeId) {
                return character.findValue("characterId").asLong();
            }
        }

        throw new RuntimeException("Could not find the characterId based on the arguments supplied");
    }

    @Override
    public String getCharacterInventory(int membershipTypeId, long membershipId, long characterId) throws IOException {

        String serviceUrl = "/" + membershipTypeId + "/Account/" + membershipId + "/Character/" + characterId + "/Inventory/Summary";

        return getJson(serviceUrl);
    }

    private String getJson(String serviceUrl) throws IOException {
        HttpGet request = new HttpGet(baseUrl + serviceUrl);
        request.addHeader(authHeader);
        HttpResponse response = httpClient.execute(request);
        return getContent(response);
    }

    private String getContent(HttpResponse response) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));

        StringBuilder content = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            content.append(line);
        }
        return content.toString();
    }

    private String getFirstSingleJsonField(String jsonString, String field) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);

        return root.findValue(field).asText();
    }

    /**
     * Sub-tree search ends if a field is found, so possible children of result nodes are not returned.
     */
    private List<JsonNode> getListOfJsonFields(String jsonString, String field) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);

        return root.findValues(field);
    }
}