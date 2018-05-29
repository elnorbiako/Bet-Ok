package pl.coderslab.betok.entity;

import javax.persistence.*;
import java.util.Set;

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
