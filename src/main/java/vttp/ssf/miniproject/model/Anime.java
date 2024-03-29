package vttp.ssf.miniproject.model;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Anime {
    private int id;
    private String title;
    private Double score;
    private String synopsis;
    private String imageLink;

}

