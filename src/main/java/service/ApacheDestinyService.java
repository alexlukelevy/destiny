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
        String jsonReturnData = getJson(serviceUrl);
        return getListOfJsonFields(jsonReturnData, "items");
    }

    @Override
    public ArrayList<String> getCharacterWeapons(int membershipTypeId, long membershipId) throws IOException {
        // Should I be using List here, not sure why I have to use List for root.findValues?
        List<JsonNode> allCharacterItems = getCharacterInventoryItems(membershipTypeId, membershipId);
        List<String> allWeaponItems = getWeaponsFromItemList(allCharacterItems);
        ArrayList<String> weaponSummaries = new ArrayList<>();

        for (String itemId : allWeaponItems) {
            weaponSummaries.add(getCharacterItemSummary(membershipTypeId, membershipId, itemId));
        }
        return weaponSummaries;
    }

    // How would i do TDD on this, i can't exactly pass my own JsonNode object very easily
    private String getCharacterItemSummary(int membershipTypeId, long membershipId, String itemId) throws IOException {
        long characterId = getCharacterId(membershipTypeId, membershipId);
        String serviceUrl = "/" + membershipTypeId + "/Account/" + membershipId + "/Character/" + characterId + "/Inventory/" + itemId;

        return getJson(serviceUrl);
    }

    private List<String> getWeaponsFromItemList(List<JsonNode> items) throws IOException {
        ArrayList<String> weaponBucketHashValues = new ArrayList<>();
        // There must be a better way to achieve this, shall i just make them all strings? Thanks what the API calls use...
        weaponBucketHashValues.add("1498876634");
        weaponBucketHashValues.add("2465295065");
        weaponBucketHashValues.add("953998645");

        //Could make these strings
        Map<String, String> itemBucketMap = new HashMap<>();
        ArrayList<String> itemIds = new ArrayList<>();
        ArrayList<String> itemBucketHashes = new ArrayList<>();

        // Is this always going to write the data to the aList in the same order its found in JSON?
        for (JsonNode node : items) {
            itemBucketHashes.addAll((getListOfJsonFieldsAsText(node, "bucketHash")));
            itemIds.addAll((getListOfJsonFieldsAsText(node, "itemId")));
        }

        for (int i = 0; i < itemBucketHashes.size(); i++) {
            itemBucketMap.put(itemBucketHashes.get(i), itemIds.get(i));
        }

        ArrayList<String> weaponItems = new ArrayList<>();

        for (int i = 0; i < itemBucketHashes.size(); i++) {
            if (weaponBucketHashValues.contains(itemBucketHashes.get(i))) {
                weaponItems.add(itemIds.get(i));
            }
        }

        return weaponItems;
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

    private JsonNode getFirstSingleJsonField(String jsonString, String field) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);

        return root.findValue(field);
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