package pl.coderslab.betok.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto {

        @JsonProperty("league_id")
        long apiLeagueId;
        @JsonProperty("team_name")
        String name;

        // window for future functionality of keeping and presenting full standings from various leagues; currently not used 
        @JsonProperty("overall_league_position")
        int position;
        @JsonProperty("overall_league_PTS")
        int points;

    public TeamDto(long apiLeagueId, String name, int position, int points) {
        this.apiLeagueId = apiLeagueId;
        this.name = name;
        this.position = position;
        this.points = points;
    }

    public TeamDto() {
    }

    public long getApiLeagueId() {
        return apiLeagueId;
    }

    public void setApiLeagueId(long apiLeagueId) {
        this.apiLeagueId = apiLeagueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "TeamDto{" +
                "apiLeagueId=" + apiLeagueId +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", points=" + points +
                '}';
    }
}
