package vttp.ssf.miniproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.ssf.miniproject.model.AnimeDetails;
import vttp.ssf.miniproject.model.AnimeWatchList;
import vttp.ssf.miniproject.repo.WatchListRepo;

@Service
public class WatchListService {

    @Autowired
    WatchListRepo watchListRepo;

    public void addToWatchList(String username, AnimeWatchList animeWL){
        watchListRepo.addToWatchList(username, animeWL);
    }

    public List<AnimeWatchList> getWatchList(String username){
        return watchListRepo.getWatchList(username);
    }

    public void deleteFromWatchList(String username, String animeId){
        watchListRepo.deleteFromWatchList(username, animeId);
    }

    public AnimeWatchList getAnimeFromWatchList(String username, String animeId){
        return watchListRepo.getAnimeFromWatchList(username, animeId);
    }

    //--------------------------------------------------------------------------//
    // public void addToWatchList(String username, AnimeDetails animeDetails){
    //     String animeId = String.valueOf(animeDetails.getId());
    //     watchListRepo.addToWatchList(username, animeId);
    // }

    // public List<String> getWatchList(String username){
    //     return watchListRepo.getWatchList(username);
    // }

    // public void removeFromWatchList(String username, String animeId) {
    //     // String animeId = String.valueOf(animeDetails.getId());
    //     watchListRepo.removeFromWatchList(username, animeId);
    // }



}
