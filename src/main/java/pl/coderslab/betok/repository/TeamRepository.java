package pl.coderslab.betok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.betok.entity.Country;
import pl.coderslab.betok.entity.Team;


public interface TeamRepository extends JpaRepository<Team, Long> {

        Team findByName(String name);

}
