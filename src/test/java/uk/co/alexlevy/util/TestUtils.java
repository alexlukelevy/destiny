package uk.co.alexlevy.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TestUtils {

    public static JsonNode asJsonNode(String json) throws IOException {
        return new ObjectMapper().readTree(json);
    }

}