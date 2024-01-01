package vttp.ssf.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import vttp.ssf.miniproject.model.Anime;
import vttp.ssf.miniproject.model.AnimeDetails;
import vttp.ssf.miniproject.service.AnimeService;

@Controller
@RequestMapping("/")
public class AnimeController {

    @Autowired
    AnimeService animeSvc;

    @GetMapping({"/","index.html"})
    public ModelAndView landingPage(HttpSession session){
        ModelAndView mav = new ModelAndView("index");
        List<Anime> topAnimeList = animeSvc.getTopAnimes();
        String username = (String)session.getAttribute("username");
        mav.addObject("topAnimeList", topAnimeList);
        mav.addObject("username", username);

        return mav;
    }

    @GetMapping("/search")
    public ModelAndView searchAnime(@RequestParam String name, HttpSession session){

        ModelAndView mav = new ModelAndView("searchanime");
        List<Anime> animeList = animeSvc.searchAnime(name);
        String username = (String)session.getAttribute("username");

        mav.addObject("animeList", animeList);
        mav.addObject("username", username);

        return mav;
    }

    @GetMapping("/anime/{id}")
    public ModelAndView getAnimeDetails(@PathVariable("id") int id, HttpSession session){
        ModelAndView mav = new ModelAndView("animedetails");

        AnimeDetails animeDetails = animeSvc.getAnimeDetails(id);
        String username = (String)session.getAttribute("username");

        mav.addObject("animeDetails", animeDetails);
        mav.addObject("username", username);

        return mav;
    }
}
