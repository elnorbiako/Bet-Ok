package pl.coderslab.betok.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.coderslab.betok.service.BetService;
import pl.coderslab.betok.service.EventService;

/**
 * Supporting class for Scheduled events - right now it consists of two task - simulating matches (no use in real conditions,
 * as app is working on data from API Football, so real results will be in use) and bet verificator - critical task, which
 * resolve if a active bet is still active, or passive (won/lost).
 *
 * For normal conditions there will be tasks for synchronizing data from API Football - events, standings, Live Matches,
 * odds, and so on.
 */

@Component
public class ScheduledTasks {


   final private EventService eventService;
   final private BetService betService;

    public ScheduledTasks(EventService eventService, BetService betService) {
        this.eventService = eventService;
        this.betService = betService;
    }


    // @Scheduled(cron = "0 0/15 * 1/1 * ?")
    @Scheduled(fixedRate = 60000)
    public void simulateUpcomingEvents() {

        eventService.simulateUpcomingEvents();

        //  System.out.println("Yes, I'm simualting!");
    }

    @Scheduled(fixedRate = 10000)
    public void verifyBets() {
        betService.betVerificator();
    }
}
