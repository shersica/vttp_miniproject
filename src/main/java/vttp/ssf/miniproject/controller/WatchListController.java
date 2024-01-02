package vttp.ssf.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import vttp.ssf.miniproject.model.AnimeDetails;
import vttp.ssf.miniproject.model.AnimeWatchList;
import vttp.ssf.miniproject.model.WatchlistDetails;
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

        // List<AnimeDetails> watchList = new ArrayList<>();
        List<AnimeWatchList> watchList = watchListSvc.getWatchList(username);

        // List<String> animeIdList = watchListSvc.getWatchList(username);
        // for(String id : animeIdList){
        //     AnimeDetails animeDetails = animeSvc.getAnimeDetails(Integer.valueOf(id));
            
        //     //to prevent rate limit (3 request per second) 
        //     try {
        //         Thread.sleep(500);
        //     } catch (InterruptedException e) {
        //         // e.printStackTrace();
        //     }
        // }

        mav.addObject("watchList", watchList);
        mav.addObject("username", username);
        // mav.addObject("watchListTest", watchListTest);
        mav.setViewName("watchlist");

        return mav;
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateWatchList(@PathVariable("id") int id, HttpSession session){
        ModelAndView mav = new ModelAndView("updatewatchlist");
        String username = (String) session.getAttribute("username");
        AnimeWatchList animeWL = new AnimeWatchList();
        animeWL = watchListSvc.getAnimeFromWatchList(username, String.valueOf(id));
        WatchlistDetails watchlistDetails = new WatchlistDetails();

        mav.addObject("animeWL", animeWL);
        mav.addObject("watchlistDetails", watchlistDetails);
        mav.addObject("username", username);

        return mav;
    }

    @PostMapping("/update/{id}")
    public String updateWatchList(@PathVariable("id") int id,@ModelAttribute WatchlistDetails watchlistDetails, Model model, HttpSession session){
        String username = (String) session.getAttribute("username");
        System.out.println(watchlistDetails);

        AnimeWatchList anime = watchListSvc.getAnimeFromWatchList(username, String.valueOf(id));
        anime.setEpProgress(watchlistDetails.getEpProgress());
        anime.setNotes(watchlistDetails.getNotes());
        anime.setUserScore(watchlistDetails.getUserScore());
        anime.setWatchStatus(watchlistDetails.getWatchStatus());
        System.out.println(anime);

        watchListSvc.addToWatchList(username, anime);
        
        return "redirect:/watchlist";
    }

    @PostMapping("/add")
    public String saveToWatchList(@ModelAttribute("animeDetails") AnimeDetails animeDetails, HttpSession session){
        if((String) session.getAttribute("username") == null){
            System.out.println("Please login to add to your watchlist");
            return "redirect:/account/login";
        }        

        String username = (String) session.getAttribute("username");

        // watchListSvc.addToWatchList(username, animeDetails);

        AnimeWatchList animeWL = new AnimeWatchList();
        animeWL.setId(animeDetails.getId());
        animeWL.setImageLink(animeDetails.getImageLink());
        animeWL.setScore(animeDetails.getScore());
        animeWL.setEpisodes(animeDetails.getEpisodes());
        animeWL.setStatus(animeDetails.getStatus());
        animeWL.setTitle(animeDetails.getTitle());
        watchListSvc.addToWatchList(username, animeWL);

        //if possible popup notif to show it gt added, stay same page
        System.out.println(animeDetails.getTitle() + " with id of " + animeDetails.getId() + " added to watchlist");
        return "redirect:/watchlist";
    }

    @PostMapping("/delete")
    public String deleteFromWatchList(@RequestParam String animeId, HttpSession session){

        String username = (String) session.getAttribute("username");
        // watchListSvc.removeFromWatchList(username, animeId);
        watchListSvc.deleteFromWatchList(username, animeId);
        return "redirect:/watchlist";
    }
    
}
