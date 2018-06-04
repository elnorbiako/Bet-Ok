package pl.coderslab.betok.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.betok.entity.Event;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.service.EventService;
import pl.coderslab.betok.service.UserService;

@Controller
public class BetController {


    final private UserService userService;
    final private EventService eventService;


    public BetController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }


    @GetMapping("/bet")
    public String home(@RequestParam(value = "id", required = true) long eventId,
                       @RequestParam(value = "odd", required = true) String odd,
                       Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        model.addAttribute("user", user);

        Event event = eventService.findById(eventId);
        model.addAttribute("event", event);

        model.addAttribute("odd", odd);

        return "BetForm";
    }
}
