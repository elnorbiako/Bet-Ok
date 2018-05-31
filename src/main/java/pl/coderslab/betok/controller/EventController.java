package pl.coderslab.betok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.betok.dto.TeamDto;
import pl.coderslab.betok.dto.TvsTDto;
import pl.coderslab.betok.entity.Event;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.service.EventService;
import pl.coderslab.betok.service.UserService;

import java.util.List;

@Controller
public class EventController {

    private final UserService userService;
    private final EventService eventService;




    @Autowired
    public EventController(UserService userService,
                           EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }


    @GetMapping("/event")
    public String home(@RequestParam(value = "id", required = true) long id, Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        model.addAttribute(user);

        Event event = eventService.findById(id);
        model.addAttribute("event", event);

        String url = "https://apifootball.com/api/?action=get_H2H&firstTeam=" + event.getHomeTeamName() + "&secondTeam=" + event.getAwayTeamName() + "&APIkey=c4e29ebddc8c975cfd056599e5421d65d34ea41eab067765b95e776416e59cb6";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TvsTDto[]> responseTvsT = restTemplate.getForEntity(
                url, TvsTDto[].class);
        TvsTDto[] history = responseTvsT.getBody();
        model.addAttribute("history", history);
        return "user/EventView";
    }
}
