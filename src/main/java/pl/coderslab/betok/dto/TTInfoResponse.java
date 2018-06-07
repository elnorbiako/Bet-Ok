package pl.coderslab.betok.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.coderslab.betok.entity.Country;

import java.util.List;

/**
 * Additional DTO class for translating JSONs regarding Team vs Team info
 */
public class TTInfoResponse {

    @JsonProperty("firstTeam_VS_secondTeam")
    private List<TvsTDto> TvsTs;

    public List<TvsTDto> getTvsTs() {
        return TvsTs;
    }

    public void setTvsTs(List<TvsTDto> tvsTs) {
        TvsTs = tvsTs;
    }
}
