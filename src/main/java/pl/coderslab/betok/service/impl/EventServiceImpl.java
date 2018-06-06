package pl.coderslab.betok.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Event;
import pl.coderslab.betok.repository.EventRepository;
import pl.coderslab.betok.service.EventService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class EventServiceImpl implements EventService {


    private final EventRepository eventRepository;

//    private final EventService eventService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        //this.eventService = eventService;
    }


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
        futureEvent.setResult("0");

        futureEvent.setOdd_1(BigDecimal.valueOf((r.nextInt(4)+1) + r.nextDouble()));
        futureEvent.setOdd_x(BigDecimal.valueOf((r.nextInt(4)+1) + r.nextDouble()));
        futureEvent.setOdd_2(BigDecimal.valueOf((r.nextInt(4)+1) + r.nextDouble()));

        eventRepository.save(futureEvent);
    }

    @Override
    public void generateFutureEventShort() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Random r = new Random();
        long delay = r.nextInt(5)+1;
        long idSalt = r.nextInt(100000)+50000;
        Event futureEvent = new Event();
        Event event = eventRepository.findRandomEvent();
        futureEvent.setStatus("SCHEDULED");
        futureEvent.setDate(LocalDate.now());
        futureEvent.setId(event.getId()+idSalt);
        futureEvent.setLive(0);
        futureEvent.setTime(LocalTime.now().plusMinutes(delay).format(formatter));
        futureEvent.setHomeTeamName(event.getHomeTeamName());
        futureEvent.setAwayTeamName(event.getAwayTeamName());
        futureEvent.setHomeTeam(event.getHomeTeam());
        futureEvent.setAwayTeam(event.getAwayTeam());
        futureEvent.setLeague(event.getLeague());
        futureEvent.setResult("0");

        futureEvent.setOdd_1(BigDecimal.valueOf((r.nextInt(4)+1) + r.nextDouble()));
        futureEvent.setOdd_x(BigDecimal.valueOf((r.nextInt(4)+1) + r.nextDouble()));
        futureEvent.setOdd_2(BigDecimal.valueOf((r.nextInt(4)+1) + r.nextDouble()));

        eventRepository.save(futureEvent);

    }

    @Override
    public List<Event> find10UpcomingEvents(String status) {
        return eventRepository.findTop10ByStatusOrderByDateAsc(status);
    }

    @Override
    public List<Event> find10LastEvents(String status) {
        return eventRepository.findTop10ByStatusOrderByDateDesc(status);
    }

    @Override
    public List<Event> find3UpcomingEventsForTeam(String status, String teamName1, String teamName2) {
        return eventRepository.findTop3ByStatusAndHomeTeamNameOrAwayTeamNameOrderByDate(status, teamName1, teamName2);
    }

    @Override
    public List<Event> findAllScheduledEvents(String status) {
        return eventRepository.findAllByStatusOrderByDateAsc(status);
    }

    @Override
    public Event findById(long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public List<Event> findHomeByTeamName(String name, String status) {
        return eventRepository.findTop5ByHomeTeamNameAndStatusOrderByDateDesc(name, status);
    }

    @Override
    public List<Event> findAwayByTeamName(String name, String status) {
        return eventRepository.findTop5ByAwayTeamNameAndStatusOrderByDateDesc(name, status);
    }

    @Override
    public void simulateUpcomingEvents() {

        Random r = new Random();

        List<Event> events = findAllScheduledEvents("SCHEDULED");

        for (Event event : events) {

            if (event.getStatus().equals("SCHEDULED")) {

                LocalDateTime eventDate = LocalDateTime.parse(event.getDate() + " " + event.getTime(), formatter);

                if (eventDate.isBefore(LocalDateTime.now())) {

                    event.setStatus("FT");
                    event.setHomeGoals(r.nextInt(6));
                    event.setAwayGoals(r.nextInt(6));

                    if (event.getHomeGoals() > event.getAwayGoals()) {
                        event.setResult("1");
                    } else if (event.getHomeGoals() < event.getAwayGoals()) {
                        event.setResult("2");
                    } else if (event.getHomeGoals() == event.getAwayGoals()) {
                        event.setResult("X");
                    }

                    eventRepository.save(event);

                }
            }

        }

    }
}
