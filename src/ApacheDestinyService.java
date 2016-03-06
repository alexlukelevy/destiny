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

    //do we add method for get specific element, with parameter of string for find value?
    @Override
    public String getAccountSummary(int membershipTypeId, long membershipId) throws IOException {
        String serviceUrl = "/" + membershipTypeId + "/Account/" + membershipId + "/Summary";
        return getJson(serviceUrl);
    }

    @Override
    public String getCharacterSummary(int membershipTypeId, long membershipId) throws IOException {
        String accountSummaryJson = getAccountSummary(membershipTypeId, membershipId);
        List<JsonNode> allCharacters = getListOfJsonField(accountSummaryJson, "characterId");
        long characterId;

        if (allCharacters.size() == 1) {
            characterId = allCharacters.get(0).asLong();
        }
        //Is it bad to wrong to cast this here?
        else {
            characterId = Long.parseLong(userInputSelectCharacter(allCharacters));
       }


        String serviceUrl = "/" + membershipTypeId + "/Account/" + membershipId + "/Character/" + characterId;

        return getJson(serviceUrl);
    }

    /*public String getWeapons() {
        return null;
    }

    @Override // need to get the node into here. Maybe copy and paste getMembershipId but change return type to node
    public String getImportantStats(String accountSummary) {
        return "test";
    } */

    @Override
    public long getMembershipId(int membershipTypeId, String username) throws IOException {
        String serviceUrl = "/SearchDestinyPlayer/" + membershipTypeId + "/" + username;
        String json = getJson(serviceUrl);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        return root.findValue("membershipId").asLong();
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

    //Is the below repetition bad and should it be public?
    //whats the best way to test half way through without having to make the
    //method public or modify all the code above to call it?
    private String getSingleJsonField(String jsonString, String field) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);

        //Will only ever return the first found field
        return root.findValue(field).asText();
    }

    // Returns a list of JsonNodes for the field specified, eg a list of characters or items.
    //Note that sub-tree search ends if a field is found, so possible children of result nodes are not included.
    private List<JsonNode> getListOfJsonField(String jsonString, String field) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);

        return root.findValues(field);
    }

    //Using string here because i know i can cast it after the user has input it, it will also allow the user to
    //enter a non int/long etc number without throwing an error, whats the best way to handle this?
    private String userInputSelectCharacter(List<JsonNode> listOfCharacters) {
        String userInput;
        List<String> stringListOfCharacters = new ArrayList<String>();

        for (JsonNode node : listOfCharacters) {
            stringListOfCharacters.add(node.asText());
        }

        System.out.println(stringListOfCharacters);
        System.out.println("Please select the character you want from the list above by typing in their name");

        //Confirm my knowledge of what this is actually doing with Al?
        Scanner scan = new Scanner(System.in);
        userInput = scan.nextLine();

        //Is this dreadful practice
        while (1 == 1) {

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