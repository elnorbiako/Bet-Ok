package pl.coderslab.betok.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.coderslab.betok.entity.Event;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.repository.EventRepository;
import pl.coderslab.betok.service.EventService;
import pl.coderslab.betok.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Component
public class ScheduledTasks {


    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    // @Scheduled(cron = "0 0/15 * 1/1 * ?")
    @Scheduled(fixedRate = 60000)
    public void simulateUpcomingEvents() {

        Random r = new Random();

        List<Event> events = eventService.findAllScheduledEvents("SCHEDULED");

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

        //  System.out.println("Yes, I'm simualting!");
    }
}
