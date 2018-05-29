package pl.coderslab.betok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.betok.entity.Country;
import pl.coderslab.betok.entity.Event;


public interface EventRepository extends JpaRepository<Event, Long> {



}
