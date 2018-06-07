package pl.coderslab.betok.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Entity for representing countries. Right now its if filled from API football, where it gathers all leagues from
 * particular country (and probably national teams). Right now there is no further use of it.
 * In future it can be connected to {@link User} {@link Address} to determine Favorites proposals,
 * listing of Events, localisation based promotions etc.
 */
@Entity
@Table(name = "countries")
public class Country {

    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<League> leagues;


    public Country() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(Set<League> leagues) {
        this.leagues = leagues;
    }
}
