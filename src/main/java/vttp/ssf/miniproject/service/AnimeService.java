package vttp.ssf.miniproject.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import vttp.ssf.miniproject.model.Anime;
import vttp.ssf.miniproject.model.AnimeDetails;

@Service
public class AnimeService {

    private List<Anime> animeSearchList = new ArrayList<>();
    
    public List<Anime> searchAnime(String name){
        
        String url = UriComponentsBuilder
            .fromUriString("https://api.jikan.moe/v4/anime")
            .queryParam("q", name)
            .queryParam("sfw", true)
            .toUriString();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.getForEntity(url, String.class);
        String payload = resp.getBody();

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject result = reader.readObject();
        JsonArray animeArray = result.getJsonArray("data");

        animeSearchList = animeArray.stream()
                .map(j -> j.asJsonObject())
                .map(o -> {
                    int id = o.getInt("mal_id");
                    String imageLink = o.getJsonObject("images").getJsonObject("jpg").getString("image_url");
                    String title = o.getString("title");
                    // Double score = o.getJsonNumber("score").doubleValue();
                    Double score = Optional.ofNullable(o.get("score"))
                        .filter(jsonValue -> jsonValue.getValueType() == JsonValue.ValueType.NUMBER)
                        .map(jsonValue -> ((JsonNumber) jsonValue).doubleValue())
                        .orElse(null);
                    String synopsis = o.getString("synopsis", "No synopsis");
                    return new Anime(id, title, score, synopsis, imageLink);
                })
                .toList();

        // for(JsonValue anime : animeArray){
        //     JsonObject animeObject = anime.asJsonObject();

        //     int id = animeObject.getInt("mal_id");
        //     String imageLink = animeObject.getJsonObject("images").getJsonObject("jpg").getString("image_url");
        //     String title = animeObject.getString("title");
        //     // Double score = anime.asJsonObject().getJsonNumber("score").doubleValue();
        //     Double score = null;
        //     JsonValue scoreJsonValue = animeObject.get("score");
        //     if (scoreJsonValue != null && !JsonValue.NULL.equals(scoreJsonValue)) {
        //         if (scoreJsonValue.getValueType() == JsonValue.ValueType.NUMBER) {
        //             score = ((JsonNumber) scoreJsonValue).doubleValue();
        //         }
        //         else{
        //             score = 0.0;
        //         }
        //     }
        //     // String synopsis = anime.asJsonObject().getString("synopsis");
        //     String synopsis = extractSynopsis(animeObject);
        //     // String synopsis = anime.asJsonObject().getOrDefault("synopsis", JsonValue.NULL).toString().replace("\\n\\n", "\n\n");
        //     animeSearchList.add(new Anime(id, title, score, synopsis, imageLink));
        // }

        return animeSearchList;

    }

    private String extractSynopsis(JsonObject animeObject) {
        JsonValue synopsis = animeObject.get("synopsis");

        if (synopsis != null) {
            if (synopsis.getValueType() == JsonValue.ValueType.STRING) {
                return ((JsonString) synopsis).getString();
            } else if (synopsis.getValueType() == JsonValue.ValueType.OBJECT) {
                JsonObject synopsisObject = (JsonObject) synopsis;
                return synopsisObject.getString("text", "");
            }
        }
        return "";
    }

    public AnimeDetails getAnimeDetails(int id){
        
        String url = UriComponentsBuilder
        .fromUriString("https://api.jikan.moe/v4/anime/{id}/full")
        .buildAndExpand(id)
        .toUriString();
        
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.getForEntity(url, String.class);
        String payload = resp.getBody();

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject result = reader.readObject();
        JsonObject animeDetails = result.getJsonObject("data");

        int mal_id = animeDetails.getInt("mal_id");
        String title = animeDetails.getString("title");
        String synopsis = animeDetails.getString("synopsis", "No synopsis");        
        Double score = 0.0;
        JsonValue scoreJsonValue = animeDetails.get("score");
        if (scoreJsonValue != null && !JsonValue.NULL.equals(scoreJsonValue)) {
            score = ((JsonNumber) scoreJsonValue).doubleValue();
        }

        String imageLink = animeDetails.getJsonObject("images").getJsonObject("jpg").getString("image_url");
        String status = animeDetails.getString("status", "N/A");
        String season = animeDetails.getString("season", "N/A");
        String background = animeDetails.getString("background", "No background information");
        //fixed year default value to not show in html if is 0
        int year = animeDetails.getInt("year", 0);
        String trailer = animeDetails.getJsonObject("trailer").getString("embed_url", "");
        int episodes = animeDetails.getInt("episodes");
        String type = animeDetails.getString("type", "N/A");
        String source = animeDetails.getString("source", "N/A");
        String duration = animeDetails.getString("duration");
        String rating = animeDetails.getString("rating");
        String aired = animeDetails.getJsonObject("aired").getString("string", "N/A");

        
        return new AnimeDetails(mal_id, title, score, synopsis, imageLink, status, background, season, year, trailer, episodes, type, source, duration, rating, aired);

    }

    public List<Anime> getTopAnimes(){
        String url = UriComponentsBuilder
            .fromUriString("https://api.jikan.moe/v4/top/anime")
            .queryParam("sfw", true)
            .toUriString();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.getForEntity(url, String.class);
        String payload = resp.getBody();

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject result = reader.readObject();
        JsonArray animeArray = result.getJsonArray("data");

        List<Anime> topAnimeList = animeArray.stream()
                .map(j -> j.asJsonObject())
                .map(o -> {
                    int id = o.getInt("mal_id");
                    String imageLink = o.getJsonObject("images").getJsonObject("jpg").getString("image_url");
                    String title = o.getString("title");
                    Double score = Optional.ofNullable(o.get("score"))
                        .filter(jsonValue -> jsonValue.getValueType() == JsonValue.ValueType.NUMBER)
                        .map(jsonValue -> ((JsonNumber) jsonValue).doubleValue())
                        .orElse(null);
                    String synopsis = o.getString("synopsis", "No synopsis");
                    return new Anime(id, title, score, synopsis, imageLink);
                })
                .toList();
        
        return topAnimeList;
    }

}
