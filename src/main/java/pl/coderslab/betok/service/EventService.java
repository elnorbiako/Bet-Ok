package pl.coderslab.betok.service;

import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Event;

import java.time.LocalDate;
import java.util.List;


public interface EventService {

    List<Event> findTop25EventsOrderByDate();

    Event findRandomEvent();

    void generateFutureEvent();

    List<Event> find10UpcomingEvents(String status);

    List<Event> findAllScheduledEvents(String status);

    Event findById(long id);
}
