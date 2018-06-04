package pl.coderslab.betok.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.coderslab.betok.service.EventService;

@Component
public class ScheduledTasks {


    @Autowired
    private EventService eventService;



    // @Scheduled(cron = "0 0/15 * 1/1 * ?")
    @Scheduled(fixedRate = 60000)
    public void simulateUpcomingEvents() {

        eventService.simulateUpcomingEvents();

        //  System.out.println("Yes, I'm simualting!");
    }
}
