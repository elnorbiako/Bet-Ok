package pl.coderslab.betok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.betok.entity.Event;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.service.EventService;
import pl.coderslab.betok.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;
    private final EventService eventService;




    @Autowired
    public HomeController(UserService userService,
                          EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }


    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        model.addAttribute(user);

        List<Event> events = eventService.find10UpcomingEvents("SCHEDULED");
        model.addAttribute("events", events);
        return "Home";
    }
}
