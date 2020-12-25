package com.lms.api;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Batch<T> {
    @JsonProperty private int matchCount;
    @JsonProperty private List<T> items = new ArrayList<>();

    public Batch<T> matchCount(int matchCount) {
        this.matchCount = matchCount;
        return this;
    }

    @Schema(description = "Matched count")
    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public Batch<T> items(List<T> items) {
        this.items = items;
        return this;
    }

    @Schema(description = "Items of the list")
    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}

