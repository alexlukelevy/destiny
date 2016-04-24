import com.fasterxml.jackson.databind.JsonNode;

public class DestinyCharacter {

    private long characterId;
    private JsonNode characterItems;


    public JsonNode getCharacterItems() {
        return characterItems;
    }

    public void setCharacterItems(JsonNode characterItems) {
        this.characterItems = characterItems;
    }

    public long getCharacterId() {
        return characterId;
    }

}
