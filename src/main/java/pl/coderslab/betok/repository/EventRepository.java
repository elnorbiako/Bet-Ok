package pl.coderslab.betok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.betok.entity.Country;
import pl.coderslab.betok.entity.Event;

import java.time.LocalDate;
import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {

        @Query(value="SELECT * FROM events ORDER BY RAND() LIMIT 1", nativeQuery = true)
        Event findRandomEvent();

        List<Event> findTop10ByStatusOrderByDateAsc(String status);

        List<Event> findAllByStatusOrderByDateAsc(String status);

}
