package vttp.ssf.miniproject.controller;

import java.util.ArrayList;
import java.util.List;

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
        List<String> animeIdList = watchListSvc.getWatchList(username);
        List<AnimeDetails> watchList = new ArrayList<>();
        for(String id : animeIdList){
            AnimeDetails animeDetails = animeSvc.getAnimeDetails(Integer.valueOf(id));
            watchList.add(animeDetails);
        }

        JsonObjectBuilder userBuilder = Json.createObjectBuilder();
        userBuilder.add("username", username);

        JsonObjectBuilder animeBuilder = Json.createObjectBuilder();
        JsonArrayBuilder watchListBuilder = Json.createArrayBuilder();

        for(AnimeDetails anime : watchList){
            animeBuilder.add("title", anime.getTitle());
            animeBuilder.add("id", anime.getId());
            animeBuilder.add("score", anime.getScore());
            animeBuilder.add("episodes", anime.getEpisodes());
            animeBuilder.add("imageLink", anime.getImageLink());
            animeBuilder.add("status", anime.getStatus());

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
