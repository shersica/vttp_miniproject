package vttp.ssf.miniproject.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf.miniproject.model.AnimeWatchList;


@Repository
public class WatchListRepo {
    
    @Autowired @Qualifier("myredis")
    private RedisTemplate<String,String> template;

    @Autowired @Qualifier("redisObject")
    private RedisTemplate<String,Object> templateObj;

    private String WATCHLIST_PREFIX = "watchlist_";
    private HashOperations<String,String,Object> hOps;
    private ListOperations<String,String> listOps;

    public void addToWatchList(String username, AnimeWatchList animeWL){
        hOps = templateObj.opsForHash();
        hOps.put(username, String.valueOf(animeWL.getId()), animeWL);
    }

    public List<AnimeWatchList> getWatchList(String username){
        hOps = templateObj.opsForHash();
        List<AnimeWatchList> watchList = new ArrayList<>();
        if(templateObj.hasKey(username)){
            Map<String, Object> watchListEntries = hOps.entries(username);
            for(String i : watchListEntries.keySet()){
                AnimeWatchList animeWL = (AnimeWatchList) watchListEntries.get(i);
                watchList.add(animeWL);
            }
        }
        return watchList;
    }

    public void deleteFromWatchList(String username, String animeId){
        hOps = templateObj.opsForHash();
        hOps.delete(username, animeId);
    }

    public boolean hasWatchList(String username){
        return templateObj.hasKey(username);
    }

    public AnimeWatchList getAnimeFromWatchList(String username, String animeId){
        hOps = templateObj.opsForHash();
        AnimeWatchList animeWL = new AnimeWatchList();
        if(templateObj.hasKey(username)){
            Map<String,Object> watchListEntries = hOps.entries(username);
            animeWL = (AnimeWatchList)watchListEntries.get(animeId);
        }
        return animeWL;
    }


    //------------------------------------------------------------------------------//

    // public void addToWatchList(String username, String animeId){
    //     listOps = template.opsForList();
    //     String key = WATCHLIST_PREFIX + username;
    //     listOps.rightPush(key, animeId);
    // }

    // public List<String> getWatchList(String username) {
    //     String key = WATCHLIST_PREFIX + username;
    //     listOps = template.opsForList();
        
    //     // Get all elements from the list
    //     return listOps.range(key, 0, -1); 
    // }

    // public void removeFromWatchList(String username, String animeId) {
    //     String key = WATCHLIST_PREFIX + username;
    //     listOps = template.opsForList();
        
    //     // Remove the first occurrence of animeId from the list
    //     listOps.remove(key, 0, animeId); 
    // }

    // public boolean hasWatchList(String username){
    //     return template.hasKey(WATCHLIST_PREFIX + username);
    // }
    
}
