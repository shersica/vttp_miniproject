package vttp.ssf.miniproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistDetails {
    private String notes;
    private double userScore;
    private String watchStatus;
    private int epProgress;

}
