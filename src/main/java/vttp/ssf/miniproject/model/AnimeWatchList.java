package vttp.ssf.miniproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeWatchList {
    
    private int id;
    private String title;
    private String imageLink;
    private int episodes;
    private String status;
    private double score;
    private String notes;
    private double userScore;
    private String watchStatus;
    private int epProgress;

}
