package pl.coderslab.betok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Event;
import pl.coderslab.betok.repository.EventRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;


    @Override
    public List<Event> findTop25EventsOrderByDate() {
        return null;
    }

    @Override
    public Event findRandomEvent() {
        return eventRepository.findRandomEvent();
    }

    @Override
    public void generateFutureEvent() {
        Random r = new Random();
        long delay = r.nextInt(5)+1;
        long idSalt = r.nextInt(100000)+50000;
        Event futureEvent = new Event();
        Event event = eventRepository.findRandomEvent();
        futureEvent.setStatus("SCHEDULED");
        futureEvent.setDate(LocalDate.now().plusDays(delay));
        futureEvent.setId(event.getId()+idSalt);
        futureEvent.setLive(0);
        futureEvent.setTime(event.getTime());
        futureEvent.setHomeTeamName(event.getHomeTeamName());
        futureEvent.setAwayTeamName(event.getAwayTeamName());
        futureEvent.setHomeTeam(event.getHomeTeam());
        futureEvent.setAwayTeam(event.getAwayTeam());
        futureEvent.setLeague(event.getLeague());

        eventRepository.save(futureEvent);
    }

    @Override
    public List<Event> find10UpcomingEvents(String status) {
        return eventRepository.findTop10ByStatusOrderByDateAsc(status);
    }

    @Override
    public List<Event> findAllScheduledEvents(String status) {
        return eventRepository.findAllByStatusOrderByDateAsc(status);
    }

    @Override
    public Event findById(long id) {
        return eventRepository.findById(id).orElse(null);
    }
}
