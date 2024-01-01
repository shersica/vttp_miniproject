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

    public JsonObject toJson() {
        JsonObject jsonObject = Json.createObjectBuilder()
            .add("id", this.id)
            .add("title", this.title)
            .add("score", this.score)
            .add("synopsis", this.synopsis)            
            .add("imageLink", imageLink)
            .build();
        
        return jsonObject;
    }

}

