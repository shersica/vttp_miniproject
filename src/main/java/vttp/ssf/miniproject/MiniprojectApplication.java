package vttp.ssf.miniproject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.ssf.miniproject.model.Anime;
import vttp.ssf.miniproject.service.AnimeService;

@SpringBootApplication
public class MiniprojectApplication {

	@Autowired
	AnimeService animeSvc;

	public static void main(String[] args) {
		SpringApplication.run(MiniprojectApplication.class, args);
	}

	// @Override
	// public void run(String... args) throws Exception {
	// 	List<Anime> animeList = animeSvc.searchAnime("spy x family");
	// 	for(Anime anime : animeList){
	// 		System.out.printf("Title:%s, Image: %s \n", anime.getTitle(), anime.getImageLink());
	// 		System.out.println("rating:" + anime.getScore());
	// 		System.out.println("Synopsis:" + anime.getSynopsis());
	// 	}
	// }

}
