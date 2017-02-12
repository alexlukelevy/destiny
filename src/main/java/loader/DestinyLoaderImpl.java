package loader;

import com.fasterxml.jackson.databind.JsonNode;
import entities.*;
import service.DestinyService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DestinyLoaderImpl implements DestinyLoader {

    private DestinyService destinyService;

    public DestinyLoaderImpl(DestinyService destinyService) {
        this.destinyService = destinyService;
    }

    @Override
    public long getMembershipId(String username) throws IOException {
        JsonNode root = destinyService.getMembership(2, username);
        return root.findValue("membershipId").asLong();
    }

    @Override
    public List<DestinyCharacter> getCharacters(long membershipId) throws IOException {
        JsonNode root = destinyService.getAccountSummary(2, membershipId);

        JsonNode charactersNode = root.findValue("characters");

        List<DestinyCharacter> characters = new ArrayList<>();

        for (JsonNode character : charactersNode) {
            JsonNode base = character.findValue("characterBase");
            long id = base.findValue("characterId").asLong();
            int classId = base.findValue("classType").asInt();
            int level = character.findValue("characterLevel").asInt();

            characters.add(new DestinyCharacter(id, CharacterClass.fromId(classId), level));
        }

        return characters;
    }

    @Override
    public Inventory getInventory(long membershipId, long characterId) throws IOException {
        JsonNode root = destinyService.getInventory(2, membershipId, characterId);

        Map<String, JsonNode> bucketDefinitions = getDefinitions("bucket", root);
        Map<String, JsonNode> itemDefinitions = getDefinitions("item", root);

        JsonNode bucketsNode = root.findValue("Response").findValue("data").findPath("buckets").findValue("Equippable");

        List<Bucket> weapons = new ArrayList<>();
        List<Bucket> armour = new ArrayList<>();

        for (JsonNode bucket : bucketsNode) {
            String bucketHash = bucket.findValue("bucketHash").asText();
            JsonNode bucketDef = bucketDefinitions.get(bucketHash);
            String bucketName = bucketDef.findValue("bucketName").asText();
            String bucketIdentifier = bucketDef.findValue("bucketIdentifier").asText();

            if (WeaponBucketIdentifiers.contains(bucketIdentifier)) {
                List<Item> items = getItems(bucket.findValue("items"), itemDefinitions);
                weapons.add(new Bucket(bucketName, items));
            } else if (ArmourBucketIdentifiers.contains(bucketIdentifier)) {
                List<Item> items = getItems(bucket.findValue("items"), itemDefinitions);
                armour.add(new Bucket(bucketName, items));
            }
        }

        return new Inventory(weapons, armour);
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