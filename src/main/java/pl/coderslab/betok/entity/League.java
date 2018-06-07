package pl.coderslab.betok.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Respresentation of a League (from API football), which is connected to a {@link Country}. It helds all
 * {@link Team} that are gathered in this league for this season.
 * Can be upgraded to held actual standings for current season (and maybe a history for past ones),
 * also with scheduled sync to API Football
 *
 */
@Entity
@Table(name = "leagues")
public class League {

    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn
    private Country country;

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private Set<Team> teams;


    public League() {
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
}
