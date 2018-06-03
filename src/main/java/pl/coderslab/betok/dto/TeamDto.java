package pl.coderslab.betok.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto {

    @JsonProperty("league_id")
    long apiLeagueId;
    @JsonProperty("team_name")
    String name;

    // window for future functionality of keeping and presenting full standings from various leagues; implemented
    @JsonProperty("overall_league_position")
    int position;
    @JsonProperty("overall_league_PTS")
    int points;
    @JsonProperty("overall_league_payed")
    int played;
    @JsonProperty("overall_league_W")
    int won;
    @JsonProperty("overall_league_D")
    int draw;
    @JsonProperty("overall_league_L")
    int lost;
    @JsonProperty("overall_league_GF")
    int gf;
    @JsonProperty("overall_league_GA")
    int ga;


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

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getGf() {
        return gf;
    }

    public void setGf(int gf) {
        this.gf = gf;
    }

    public int getGa() {
        return ga;
    }

    public void setGa(int ga) {
        this.ga = ga;
    }

    @Override
    public String toString() {
        return "TeamDto{" +
                "apiLeagueId=" + apiLeagueId +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", points=" + points +
                ", played=" + played +
                ", won=" + won +
                ", draw=" + draw +
                ", lost=" + lost +
                ", gf=" + gf +
                ", ga=" + ga +
                '}';
    }
}
