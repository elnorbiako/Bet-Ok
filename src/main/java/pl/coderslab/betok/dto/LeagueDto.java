package pl.coderslab.betok.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueDto {

    @JsonProperty("country_id")
    long apiCountryId;
    @JsonProperty("league_id")
    long apiLeagueId;
    @JsonProperty("league_name")
    String name;

    public LeagueDto(long apiCountryId, long apiLeagueId, String name) {
        this.apiCountryId = apiCountryId;
        this.apiLeagueId = apiLeagueId;
        this.name = name;
    }

    public LeagueDto() {
    }

    public long getApiCountryId() {
        return apiCountryId;
    }

    public void setApiCountryId(long apiCountryId) {
        this.apiCountryId = apiCountryId;
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

    @Override
    public String toString() {
        return "LeagueDto{" +
                "apiCountryId=" + apiCountryId +
                ", apiLeagueId=" + apiLeagueId +
                ", name='" + name + '\'' +
                '}';
    }

    //gettery, settery, toString
}