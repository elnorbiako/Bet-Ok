package pl.coderslab.betok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.betok.entity.Country;
import pl.coderslab.betok.entity.League;


public interface LeagueRepository extends JpaRepository<League, Long> {



}
