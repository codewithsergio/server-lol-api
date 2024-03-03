package com.sergio.lolwebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergio.lolwebapp.entity.DataProperty;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@Log
@CrossOrigin(origins = "http://127.0.0.1:5501") // Added to abide by javascript fetch rules
public class ItemController {
    @GetMapping(path = "/items")
    public DataProperty items() throws IOException {
        // TODO: delete old items still in API, such as Liandry's Lament
        //      can delete them by not including them by their ID such as 3001
        String ourJsonFilePath = "src/main/resources/static/item.json";
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(ourJsonFilePath), DataProperty.class);
    }
}