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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApacheDestinyService implements DestinyService {

    private final DefaultHttpClient httpClient;
    private final String baseUrl;
    private final Header authHeader;

    public ApacheDestinyService(String baseUrl, String apiKey) {
        this.httpClient = new DefaultHttpClient();
        this.baseUrl = baseUrl;
        this.authHeader = new BasicHeader("X-API-Key", apiKey);
    }

    //TODO Method with String input that returns a specific element
    @Override
    public String getAccountSummary(int membershipTypeId, long membershipId) throws IOException {
        String serviceUrl = "/" + membershipTypeId + "/Account/" + membershipId + "/Summary";
        return getJson(serviceUrl);
    }

    @Override
    public String getCharacterSummary(int membershipTypeId, long membershipId) throws IOException {
        long characterId = getCharacterId(membershipTypeId, membershipId);

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
    public String getCharacterInventory(int membershipTypeId, long membershipId) throws IOException {
    ///{membershipType}/Account/{destinyMembershipId}/Character/{characterId}/Activities/
        long characterId = getCharacterId(membershipTypeId, membershipId);

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

    public long getCharacterId(int membershipTypeId, long membershipId) throws IOException {
        String accountSummaryJson = getAccountSummary(membershipTypeId, membershipId);
        List<JsonNode> allCharacters = getListOfJsonFields(accountSummaryJson, "characterId");
        long characterId;

        if (allCharacters.size() == 1) {
            characterId = allCharacters.get(0).asLong();
        } else {
            characterId = Long.parseLong(userInputSelectCharacter(allCharacters));
        }

        return characterId;
    }

    private String userInputSelectCharacter(List<JsonNode> listOfCharacters) {
        String userInput;
        List<String> stringListOfCharacters = new ArrayList<>();

        for (JsonNode node : listOfCharacters) {
            stringListOfCharacters.add(node.asText());
        }

        System.out.println(stringListOfCharacters);
        System.out.println("Please select the character you want from the list above by typing in their name");

        Scanner scan = new Scanner(System.in);
        userInput = scan.nextLine();

        while (true) {

            if (stringListOfCharacters.contains(userInput)) {
                break;
            } else {
                System.out.println(userInput + " is not a valid value, please enter a value from the list below");
                System.out.println(stringListOfCharacters);
                userInput = scan.nextLine();
            }
        }
        return userInput;
    }
}