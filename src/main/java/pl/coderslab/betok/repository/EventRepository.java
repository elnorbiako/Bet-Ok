package pl.coderslab.betok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.betok.entity.Country;
import pl.coderslab.betok.entity.Event;

import java.time.LocalDate;
import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {

        /**
         * Method takes a random event from database - for purpose of generating a future event by EventService
         * {@link pl.coderslab.betok.service.impl.EventServiceImpl}
         * @return Event randomly taken from DB (so a real one from API Football)
         */
        @Query(value="SELECT * FROM events ORDER BY RAND() LIMIT 1", nativeQuery = true)
        Event findRandomEvent();

        List<Event> findTop10ByStatusOrderByDateAsc(String status);

        List<Event> findTop10ByStatusOrderByDateDescTimeDesc(String status);


        @Query(value = "SELECT * from events WHERE home_team_name OR away_team_name LIKE :teamName AND status LIKE :status", nativeQuery = true)
        List<Event> findTop3ByStatusAndHomeTeamNameOrAwayTeamNameOrderByDate(@Param("teamName") String teamName, @Param("status") String status);


        List<Event> findAllByStatusOrderByDateAsc(String status);

        List<Event> findTop5ByHomeTeamNameAndStatusOrderByDateDesc(String name, String status);

        List<Event> findTop5ByAwayTeamNameAndStatusOrderByDateDesc(String name, String status);

}
