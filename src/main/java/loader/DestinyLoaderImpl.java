package loader;

import com.fasterxml.jackson.databind.JsonNode;
import entities.*;
import service.DestinyService;

import java.io.IOException;
import java.util.*;

public class DestinyLoaderImpl implements DestinyLoader {

    private DestinyService api;

    public DestinyLoaderImpl(DestinyService api) {
        this.api = api;
    }

    @Override
    public long getMembershipId(String username) throws IOException {
        JsonNode root =  api.getMembership(2, username);
        return root.findValue("membershipId").asLong();
    }

    @Override
    public List<DestinyCharacter> getCharacters(long membershipId) {
        // TODO: to implement

        return null;
    }

    @Override
    public List<Bucket> getInventory(long membershipId, long characterId) throws IOException {
        JsonNode root = api.getInventory(2, membershipId, characterId);

        // TODO: where to filter for weapons and items?
        Set<String> identifiers = new HashSet<>();
        for (BucketIdentifiers identifier : BucketIdentifiers.values()) {
            identifiers.add(identifier.toString());
        }

        // TODO: could this be condensed into one map?
        Map<String, JsonNode> bucketDefintions = getDefinitions("bucket", root);
        Map<String, JsonNode> itemDefinitions = getDefinitions("item", root);

        JsonNode bucketsNode = root.findValue("Response").findValue("data").findPath("buckets").findValue("Equippable");

        List<Bucket> buckets = new ArrayList<>();

        for (JsonNode bucket : bucketsNode) {

            String bucketHash = bucket.findValue("bucketHash").asText();
            JsonNode bucketDef = bucketDefintions.get(bucketHash);
            String bucketName = bucketDef.findValue("bucketName").asText();
            String bucketIdentifier = bucketDef.findValue("bucketIdentifier").asText();

            if (!identifiers.contains(bucketIdentifier)) {
                continue;
            }

            List<Item> items = getItems(bucket.findValue("items"), itemDefinitions);

            buckets.add(new Bucket(bucketName, items));
        }

        return buckets;
    }

    private static List<Item> getItems(JsonNode itemsNode, Map<String, JsonNode> itemDefinitions) {
        List<Item> items = new ArrayList<>();

        for (JsonNode item : itemsNode) {
            String itemHash = item.findValue("itemHash").asText();
            JsonNode itemDef = itemDefinitions.get(itemHash);
            String tierTypeName = itemDef.findValue("tierTypeName").asText();
            String itemName = itemDef.findValue("itemName").asText();
            int lightLevel = item.findValue("primaryStat").findValue("value").asInt();
            ItemGrade itemGrade = ItemGrade.valueOf(tierTypeName);
            items.add(new Item(itemName, lightLevel, itemGrade));
        }

        return items;
    }

    private static Map<String, JsonNode> getDefinitions(String type, JsonNode root) {
        JsonNode definitionNode = root.findValue("definitions").findValue(type + "s");
        Map<String, JsonNode> definitions = new HashMap<>();

        for (JsonNode def : definitionNode) {
            definitions.put(def.findValue(type + "Hash").asText(), def);
        }
        return definitions;
    }
}