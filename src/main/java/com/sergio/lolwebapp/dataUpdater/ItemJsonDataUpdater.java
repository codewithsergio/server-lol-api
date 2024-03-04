package com.sergio.lolwebapp.dataUpdater;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergio.lolwebapp.entity.DataProperty;
import com.sergio.lolwebapp.entity.Item;
import lombok.extern.java.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Log
// THIS FILE CALLS RIOT API AND REMOVES UNWANTED INFORMATION
// (SUCH AS DEPRECATED ITEMS IDS) FROM THE JSON
public class ItemJsonDataUpdater {
    // Make sure to use latest version link for item json
    final static String ITEM_DATA_LOL_API_URL_STRING = "https://ddragon.leagueoflegends.com/cdn/14.3.1/data/en_US/item.json";
    final static String ITEM_JSON_FILE_PATH = "src/main/resources/static/item.json";
    public static void main(String[] args) throws IOException {
        // TODO: how to run this program in terminal (that's how I will run it later on).
        saveLeagueApiJsonDataToItemsFile();
    }

    public static JsonNode getLeagueApiJsonData(ObjectMapper objectMapper) throws IOException {
       URL url = new URL(ITEM_DATA_LOL_API_URL_STRING);
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
       con.setRequestMethod("GET");
       return objectMapper.readTree(con.getInputStream());
    }

    public static void saveLeagueApiJsonDataToItemsFile() throws IOException {

       ObjectMapper objectMapper = new ObjectMapper();
       JsonNode rootNode = getLeagueApiJsonData(objectMapper);

       Map<String, Item> filteredItems = new HashMap<>(); // Stores only in-game items
       JsonNode dataNode = rootNode.get("data");
       if (dataNode != null && dataNode.isObject()) {
           dataNode.fields().forEachRemaining(entry -> {
               String itemId = entry.getKey();
               // Do not include items with id starting with 7
               // These are all deprecated items
               int integerItemId = Integer.parseInt(itemId);
               if(integerItemId < 8000 && integerItemId >= 7000) {
                   System.err.print("Deleting: " + integerItemId);
                   return;
               }
               JsonNode itemNode = entry.getValue();
               // IN-GAME ITEM CRITERIA: maps:11 is true, or maps:11 isn't false,
               // or maps:11 is true and inStore doesn't exist
               boolean isInMap = itemNode.has("maps") && itemNode.get("maps").get("11").asBoolean();
               boolean inStore = itemNode.has("inStore");

               if (isInMap && !inStore) {
                   try {
                       Item item = objectMapper.treeToValue(itemNode, Item.class);
                       item.setId(itemId); // Add the items key to the item for jmespath parsing
                       filteredItems.put(itemId, item);
                   } catch (IOException e) {
                       System.err.println("Error parsing item...");
                   }
               }
           });
       }

       DataProperty result = new DataProperty();
       result.setData(filteredItems);

       objectMapper.writeValue(new File(ITEM_JSON_FILE_PATH), result);
    }
}
