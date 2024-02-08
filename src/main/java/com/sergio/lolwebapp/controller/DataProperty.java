package com.sergio.lolwebapp.controller;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataProperty {
    public Map<String, Item> getData() {
        return data;
    }

    public void setData(Map<String, Item> data) {
        this.data = data;
    }

    private Map<String, Item> data;
}
