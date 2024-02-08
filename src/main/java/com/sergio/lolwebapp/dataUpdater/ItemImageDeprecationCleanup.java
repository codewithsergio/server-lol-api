package com.sergio.lolwebapp.dataUpdater;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

// This file deletes all files in images folder if not in item.json
public class ItemImageDeprecationCleanup {
    final static String ITEM_IMAGES_FOLDER_PATH = "src/main/resources/static/itemImages/";
    final static String ITEM_JSON_FILE_PATH = "src/main/resources/static/item.json";
    public static void main(String[] args) {
        deletePngsNotInItemJsonFile();
    }

    private static void deletePngsNotInItemJsonFile(){
        Set<String> itemIds = getItemIdsFromJson();

        File folder = new File(ITEM_IMAGES_FOLDER_PATH);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".png")) {
                    String fileName = file.getName().replace(".png", "");
                    if (!itemIds.contains(fileName)) {
                        // Delete the file if its name doesn't match any item ID
                        file.delete();
                        System.out.println("Deleted: " + file.getName());
                    }
                }
            }
        }
    }

    private static Set<String> getItemIdsFromJson() {
        Set<String> itemIds = new HashSet<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(ITEM_JSON_FILE_PATH));
            JsonNode dataNode = rootNode.path("data");
            dataNode.fields().forEachRemaining(entry -> itemIds.add(entry.getKey()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemIds;
    }
}
