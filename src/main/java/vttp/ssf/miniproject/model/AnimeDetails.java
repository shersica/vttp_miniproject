package vttp.ssf.miniproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeDetails {
    
    private int id;
    private String title;
    private Double score;
    private String synopsis;
    private String imageLink;
    private String status;
    private String background;
    private String season;
    private int year;
    private String trailer;
    private int episodes;
    private String type;
    private String source;
    private String duration;
    private String rating;
    private String aired;


    
}
