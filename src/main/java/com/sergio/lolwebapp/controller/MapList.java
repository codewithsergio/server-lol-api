package com.sergio.lolwebapp.controller;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
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
public class MapList {

    @JsonAnyGetter
    public Map<String, Boolean> getMaps() {
        return maps;
    }

    @JsonAnySetter
    public void setMap(String key, Boolean value) {
        maps.put(key, value);
    }

    private Map<String, Boolean> maps;
}
