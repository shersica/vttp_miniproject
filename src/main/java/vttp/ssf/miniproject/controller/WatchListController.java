package vttp.ssf.miniproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import vttp.ssf.miniproject.model.AnimeDetails;
import vttp.ssf.miniproject.service.AnimeService;
import vttp.ssf.miniproject.service.WatchListService;

@Controller
@RequestMapping("/watchlist")
public class WatchListController {

    @Autowired
    WatchListService watchListSvc;
    
    @Autowired
    AnimeService animeSvc;

    @GetMapping
    public ModelAndView getWatchList(HttpSession session){
        ModelAndView mav = new ModelAndView();

        //check if user has login
        if((String) session.getAttribute("username") == null){
            System.out.println("Please login");
            mav.setViewName("redirect:/account/login");
            return mav;
        }
        
        String username = (String) session.getAttribute("username");
        System.out.println("Current user:" + username);

        //Check if user has watchlist
        List<AnimeDetails> watchList = new ArrayList<>();
        // if(!watchListSvc.hasWatchList(username)){
        //     watchList = new ArrayList<>();
        //     System.out.println("User has no watchlist");
        //     //how come doesnt show the test part
        //     mav.setViewName("watchlist");
        //     return mav;
        // }
        
        List<String> animeIdList = watchListSvc.getWatchList(username);
        for(String id : animeIdList){
            AnimeDetails animeDetails = animeSvc.getAnimeDetails(Integer.valueOf(id));
            watchList.add(animeDetails);
        }
        // System.out.println(username + "'s watchlist:" + watchList);

        mav.addObject("watchList", watchList);
        mav.addObject("username", username);
        mav.setViewName("watchlist");

        return mav;
    }

    @PostMapping("/add")
    public String saveToWatchList(@ModelAttribute("animeDetails") AnimeDetails animeDetails, HttpSession session){
        if((String) session.getAttribute("username") == null){
            System.out.println("Please login to add to your watchlist");
            return "redirect:/account/login";
        }        

        String username = (String) session.getAttribute("username");

        //Check if user has watchlist
        //NEED TO DEAL WITH NULL Watchlist
        // if(!watchListSvc.hasWatchList(username)){
        //     watchListSvc.createWatchList(username);
        //     System.out.println("User has no watchlist, created new one");
        // }

        watchListSvc.addToWatchList(username, animeDetails);

        //if possible popup notif to show it gt added, stay same page
        System.out.println(animeDetails.getTitle() + " with id of " + animeDetails.getId() + " added to watchlist");
        return "redirect:/watchlist";
    }

    //delete
    @PostMapping("/delete")
    public String removeFromWatchList(@RequestParam String animeId, HttpSession session){

        String username = (String) session.getAttribute("username");
        watchListSvc.removeFromWatchList(username, animeId);
        return "redirect:/watchlist";
    }
    
}
