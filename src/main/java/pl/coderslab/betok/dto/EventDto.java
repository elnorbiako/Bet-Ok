package pl.coderslab.betok.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {

    @JsonProperty("league_id")
    long apiLeagueId;
    @JsonProperty("match_id")
    long id;
    @JsonProperty("match_date")
    String date;
    @JsonProperty("match_time")
    String time;
    @JsonProperty("match_hometeam_name")
    String homeTeam;
    @JsonProperty("match_awayteam_name")
    String awayTeam;
    @JsonProperty("match_hometeam_score")
    int homeGoals;
    @JsonProperty("match_awayteam_score")
    int awayGoals;
}