package vttp.ssf.miniproject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpSession;
import vttp.ssf.miniproject.model.Anime;
import vttp.ssf.miniproject.model.AnimeDetails;
import vttp.ssf.miniproject.model.AnimeWatchList;
import vttp.ssf.miniproject.service.AnimeService;
import vttp.ssf.miniproject.service.WatchListService;

@RestController
@RequestMapping("/api")
public class AnimeRestController {

    @Autowired
    WatchListService watchListSvc;

    @Autowired
    AnimeService animeSvc;
    
    //Get current user watchlist {user} -> your username
    @GetMapping(path = "/watchlist/{user}")
    public ResponseEntity<String> getWatchListAsJson(@PathVariable("user") String user,HttpSession session){
        String username = (String) session.getAttribute("username");
        List<AnimeWatchList> animeWatchList = watchListSvc.getWatchList(username);
        List<AnimeDetails> watchList = new ArrayList<>();
        for(AnimeWatchList animeWL : animeWatchList){
            AnimeDetails animeDetails = animeSvc.getAnimeDetails(Integer.valueOf(animeWL.getId()));
            watchList.add(animeDetails);
        }

        JsonObjectBuilder userBuilder = Json.createObjectBuilder();
        userBuilder.add("username", username);

        JsonObjectBuilder animeBuilder = Json.createObjectBuilder();
        JsonArrayBuilder watchListBuilder = Json.createArrayBuilder();


        for(AnimeWatchList animeWL : animeWatchList){
            animeBuilder.add("title", animeWL.getTitle());
            animeBuilder.add("id", animeWL.getId());
            animeBuilder.add("score", animeWL.getScore());
            animeBuilder.add("episodes", animeWL.getEpisodes());
            animeBuilder.add("imageLink", animeWL.getImageLink());
            animeBuilder.add("status", animeWL.getStatus());
            animeBuilder.add("userScore", Optional.ofNullable(animeWL.getUserScore()).orElse(0.0)); 
            animeBuilder.add("epProgress", Optional.ofNullable(animeWL.getEpProgress()).orElse(0)); 
            animeBuilder.add("notes", Optional.ofNullable(animeWL.getNotes()).orElse(""));
            animeBuilder.add("watchStatus", Optional.ofNullable(animeWL.getWatchStatus()).orElse(""));
  
            watchListBuilder.add(animeBuilder);
        }

        userBuilder.add("watchlist", watchListBuilder);
        JsonObject resp = userBuilder.build();

        return ResponseEntity.ok(resp.toString());
    }

    //Search anime /api/search?name=
    @GetMapping("/search")
    public ResponseEntity<String> searchAnime(@RequestParam String name){
        List<Anime> searchResults = animeSvc.searchAnime(name);
        JsonObjectBuilder animeBuilder = Json.createObjectBuilder();
        JsonArrayBuilder searchResultsBuilder = Json.createArrayBuilder();
        
        for(Anime anime : searchResults){
            animeBuilder.add("title", anime.getTitle());
            animeBuilder.add("imageLink", anime.getImageLink());

            searchResultsBuilder.add(animeBuilder);
        }

        JsonArray resp = searchResultsBuilder.build();

        return ResponseEntity.ok(resp.toString());

    }
}
