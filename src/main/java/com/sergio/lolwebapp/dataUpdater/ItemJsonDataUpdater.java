package com.sergio.lolwebapp.dataUpdater;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergio.lolwebapp.controller.DataProperty;
import com.sergio.lolwebapp.controller.Item;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Log
// THIS FILE CALLS RIOT API AND REMOVES UNWANTED INFORMATION
// (SUCH AS DEPRECATED ITEMS) FROM THE JSON
public class ItemJsonDataUpdater {
    final static String URL_STRING = "https://ddragon.leagueoflegends.com/cdn/14.3.1/data/en_US/item.json";
    final static String JSON_FILE_PATH = "src/main/resources/static/item.json";
    public static void main(String[] args) throws IOException {
        // TODO: how to run this program in terminal (that's how I will run it later on).

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = getLeagueApiJsonData(objectMapper);

        Map<String, Item> filteredItems = new HashMap<>(); // Stores only in-game items
        JsonNode dataNode = rootNode.get("data");
        if (dataNode != null && dataNode.isObject()) {
            dataNode.fields().forEachRemaining(entry -> {
                String itemId = entry.getKey();
                JsonNode itemNode = entry.getValue();
                // IN-GAME ITEM CRITERIA: maps:11 is true, or maps:11 isn't false,
                // or maps:11 is true and inStore doesn't exist
                boolean isInMap = itemNode.has("maps") && itemNode.get("maps").get("11").asBoolean();
                boolean inStore = itemNode.has("inStore");

                if (isInMap && !inStore) {
                    try {
                        Item item = objectMapper.treeToValue(itemNode, Item.class);
                        filteredItems.put(itemId, item);
                    } catch (IOException e) {
                        log.warning("Error processing item: " + itemId);
                    }
                }
            });
        }

        DataProperty result = new DataProperty();
        result.setData(filteredItems);

        objectMapper.writeValue(new File(JSON_FILE_PATH), result);
    }

   public static JsonNode getLeagueApiJsonData(ObjectMapper objectMapper) throws IOException {
       URL url = new URL(URL_STRING);
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
       con.setRequestMethod("GET");
       return objectMapper.readTree(con.getInputStream());
   }
}
