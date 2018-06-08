package pl.coderslab.betok.service;

import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Event;

import java.time.LocalDate;
import java.util.List;


public interface EventService {

    List<Event> findTop25EventsOrderByDate();

    Event findRandomEvent();

    void generateFutureEvent();

    void generateFutureEventShort();

    List<Event> find10UpcomingEvents(String status);

     List<Event> find10LastEvents(String status);

    List<Event> find3UpcomingEventsForTeam(String teamName, String status);


    List<Event> findAllScheduledEvents(String status);

    Event findById(long id);

    List<Event> findHomeByTeamName(String name, String status);

    List<Event> findAwayByTeamName(String name, String status);

    void simulateUpcomingEvents();

    List<Event> findHomeOrAwayByStatus(String name, String status);
}
