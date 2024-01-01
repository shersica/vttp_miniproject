package vttp.ssf.miniproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.ssf.miniproject.model.AnimeDetails;
import vttp.ssf.miniproject.repo.WatchListRepo;

@Service
public class WatchListService {

    @Autowired
    WatchListRepo watchListRepo;
    

    // public void addToWatchList(String username, AnimeDetails animeDetails){
        // List<AnimeDetails> watchList = watchListRepo.getWatchList(username);
        
        // if(watchList == null){
        //     watchList = new ArrayList<>();
        // }
        // watchList.add(animeDetails);
        // watchListRepo.addToWatchList(username, watchList);
        // watchListRepo.addToWatchList(username, animeDetails);
    // }

    // public List<AnimeDetails> getWatchList(String username){
    //     return watchListRepo.getWatchList(username);

    // }

    // public void delete(String username, AnimeDetails animeDetails){
    //     List<AnimeDetails> watchList = watchListRepo.getWatchList(username);

    //     if(watchList != null){
    //         watchList.remove(animeDetails);
    //         watchListRepo.addToWatchList(username, watchList);
    //     }
    // }

    // public boolean hasWatchList(String username){
    //     return watchListRepo.hasWatchList(username);
    // }

    // public void createWatchList(String username){
    //     watchListRepo.addWatchList(username, new ArrayList<>());
    // }

    //--------------------------------------------------------------------------//
    public void addToWatchList(String username, AnimeDetails animeDetails){
        String animeId = String.valueOf(animeDetails.getId());
        watchListRepo.addToWatchList(username, animeId);
    }

    public List<String> getWatchList(String username){
        return watchListRepo.getWatchList(username);
    }

    public void removeFromWatchList(String username, String animeId) {
        // String animeId = String.valueOf(animeDetails.getId());
        watchListRepo.removeFromWatchList(username, animeId);
    }



}
