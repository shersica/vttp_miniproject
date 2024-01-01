package vttp.ssf.miniproject.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf.miniproject.model.AnimeDetails;


@Repository
public class WatchListRepo {
    
    @Autowired @Qualifier("myredis")
    private RedisTemplate<String,String> template;

    private String watchlist_key = "watchlist";
    private String WATCHLIST_PREFIX = "watchlist_";
    private HashOperations<String,String,String> hOps;
    private ListOperations<String,String> listOps;

    // public boolean hasWatchList(String hashKey){
    //     //update the keyname (is not username), can be username_watchlist or smth
    //     //hashKey is username
    //     hOps = template.opsForHash();
    //     if(hOps.hasKey(watchlist_key, hashKey)){
    //         return true;
    //     }
    //     return false;
    // }

    // public void addWatchList(String hashKey, List<AnimeDetails> watchList){
    //     hOps = template.opsForHash();
    //     StringBuilder sb = new StringBuilder();
    //     watchList.stream()
    //         .forEach(id -> {
    //             String rec = "%d".formatted(id.getId());
    //             sb.append(rec + ",");
    //         });
    //     String animeIds = sb.toString();
    //     hOps.put(watchlist_key, hashKey, animeIds);
    //     // System.out.println("List of Anime Ids:" + animeIds);
    // }

    // public List<AnimeDetails> getWatchList(String hashKey) {
    //     hOps = template.opsForHash();
    //     String watchListString = hOps.get(watchlist_key, hashKey);
    //     List<AnimeDetails> watchList = new ArrayList<>();
    //     return watchList;
    // }

    // public void addToWatchList(String hashKey, List<AnimeDetails> watchList) {
    //     hOps = template.opsForHash();
    //     sOps = template.opsForList();

    //     // hOps.put(watchlist_key, username, watchList);
    // }

    //------------------------------------------------------------------------------//

    public void addToWatchList(String username, String animeId){
        listOps = template.opsForList();
        String key = WATCHLIST_PREFIX + username;
        listOps.rightPush(key, animeId);
    }

    public List<String> getWatchList(String username) {
        String key = WATCHLIST_PREFIX + username;
        listOps = template.opsForList();
        
        // Get all elements from the list
        return listOps.range(key, 0, -1); 
    }

    public void removeFromWatchList(String username, String animeId) {
        String key = WATCHLIST_PREFIX + username;
        listOps = template.opsForList();
        
        // Remove the first occurrence of animeId from the list
        listOps.remove(key, 0, animeId); 
    }

    public boolean hasWatchList(String username){
        return template.hasKey(WATCHLIST_PREFIX + username);
    }
    
}
