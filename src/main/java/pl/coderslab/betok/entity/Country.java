package pl.coderslab.betok.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
