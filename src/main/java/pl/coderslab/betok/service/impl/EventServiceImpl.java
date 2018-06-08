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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service for handling all the logic regarding Events, along with generating future ones and simulating matches (no use
 * in 'normal' conditions)
 */
@Service
public class EventServiceImpl implements EventService {


    private final EventRepository eventRepository;

    /**
     * Date formatter used for combining two separate fields event.date and event.time {@link Event}
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        //this.eventService = eventService;
    }


    @Override
    public List<Event> findTop25EventsOrderByDate() {
        return null;
    }

    /**
     *
     * @return Random event from DB - for future events purposes
     */
    @Override
    public Event findRandomEvent() {
        return eventRepository.findRandomEvent();
    }

    /**
     * Method for generating a UpcomingEvent.
     * It takes a random true event from database, randomize new date (+1-5 days from now), randomize new id to have a
     * unique one in DB, sets match status to 'SCHEDULED' (so a planned event) and a result to '0' (no result).
     * Rest of information (time, teams, league) are used from original true event.
     */
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
        /**
         * This part would be normally dowloaded from extrnal odds API. Due to problems with API Football, ods are
         * randomized
         */
        futureEvent.setOdd_1(BigDecimal.valueOf((r.nextInt(4)+1) + r.nextDouble()));
        futureEvent.setOdd_x(BigDecimal.valueOf((r.nextInt(4)+1) + r.nextDouble()));
        futureEvent.setOdd_2(BigDecimal.valueOf((r.nextInt(4)+1) + r.nextDouble()));

        eventRepository.save(futureEvent);
    }

    /**
     * Method similar to generateFutureEvent, but generates a quick event (1-5 minutes from now) - mostly for test
     * and presentation purposes.
     */
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
        return eventRepository.findTop10ByStatusOrderByDateDescTimeDesc(status);
    }

    @Override
    public List<Event> find3UpcomingEventsForTeam(String teamName, String status) {
        return eventRepository.findTop3ByStatusAndHomeTeamNameOrAwayTeamNameOrderByDate(teamName, status);
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

    /**
     * Method simulates upcoming {@link Event} and determines result.
     * It constantly (via  {@link pl.coderslab.betok.scheduled.ScheduledTasks} ) checks, if date and time
     * of upcoming event is still in future. If it passes .now() match is simulated:
     *      1) Status is set to 'FT'
     *      2) Goals are randomized for two teams
     *      3) Result is determined on numbers of goals (possible options: 1, X, 2)
     *
     *  And then event is saved to DB using {@link EventRepository}
     *
     *  in 'normal conditions' this method would probably only check for scores from API Football and then set result
     *  based on a number of goals for each team.
     *
     */
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

    @Override
    public List<Event> findHomeOrAwayByStatus(String name, String status) {

        List<Event> events = new ArrayList<>();

        events.addAll(findHomeByTeamName(name, status));
        events.addAll(findAwayByTeamName(name, status));

        return events;

    }
}
