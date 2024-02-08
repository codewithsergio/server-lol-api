package com.sergio.lolwebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@Log
public class ItemController {
    @GetMapping(path = "/items")
    public DataProperty items() throws IOException {

        String ourJsonFilePath = "src/main/resources/static/item.json";
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(ourJsonFilePath), DataProperty.class);
    }
}