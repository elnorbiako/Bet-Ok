package pl.coderslab.betok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.betok.service.EventService;

@Controller
public class FutureController {

    @Autowired
    EventService eventService;

    @GetMapping("/admin/future")
    public String generateFutureEvent() {

        eventService.generateFutureEvent();

        return "redirect:/admin/panel";

    }

    @GetMapping("/admin/futureShort")
    public String generateFutureEventShort() {

        eventService.generateFutureEventShort();

        return "redirect:/admin/panel";

    }
}
