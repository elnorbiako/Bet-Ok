package pl.coderslab.betok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.betok.entity.Event;
import pl.coderslab.betok.service.EventService;


/**
 * This is controller responsible for handling web requests regarding generating future {@link Event}.
 * It won't be needed in 'normal' conditions, where Application will use real data form API football.
 *
 */
@Controller
public class FutureController {

    @Autowired
    EventService eventService;

    /**
     * GET for creating a future event in a few days from now. Accessible from Admin panel.
     * @return
     */
    @GetMapping("/admin/future")
    public String generateFutureEvent() {

        eventService.generateFutureEvent();

        return "redirect:/admin/panel";

    }

    /**
     * GET for creating a future event in a few minutes from now (used mostly for testing betting algorithm).
     * Accessible from Admin panel.
     * @return
     */

    @GetMapping("/admin/futureShort")
    public String generateFutureEventShort() {

        eventService.generateFutureEventShort();

        return "redirect:/admin/panel";

    }
}
