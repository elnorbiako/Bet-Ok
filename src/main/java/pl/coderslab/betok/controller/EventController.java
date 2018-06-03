package pl.coderslab.betok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.betok.dto.TTInfoResponse;
import pl.coderslab.betok.dto.TvsTDto;
import pl.coderslab.betok.entity.Event;
import pl.coderslab.betok.entity.Team;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.repository.TeamRepository;
import pl.coderslab.betok.service.EventService;
import pl.coderslab.betok.service.UserService;

import java.util.List;

@Controller
public class EventController {

    private final UserService userService;
    private final EventService eventService;
    private final TeamRepository teamRepository;



    @Autowired
    public EventController(UserService userService,
                           EventService eventService, TeamRepository teamRepository) {
        this.userService = userService;
        this.eventService = eventService;
        this.teamRepository = teamRepository;
    }


    @GetMapping("/event")
    public String home(@RequestParam(value = "id", required = true) long id, Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        model.addAttribute(user);

        Event event = eventService.findById(id);
        model.addAttribute("event", event);

        String url = "https://apifootball.com/api/?action=get_H2H&firstTeam=" + event.getHomeTeamName() + "&secondTeam=" + event.getAwayTeamName() + "&APIkey=c4e29ebddc8c975cfd056599e5421d65d34ea41eab067765b95e776416e59cb6";
        RestTemplate restTemplate = new RestTemplate();
        List<TvsTDto> TTs = restTemplate.getForObject(url, TTInfoResponse.class).getTvsTs();

        model.addAttribute("TTs", TTs);
        return "user/EventView";
    }

    @GetMapping("/team")
    public String home(@RequestParam(value = "name", required = true) String name, Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        model.addAttribute(user);

        Team team = teamRepository.findByName(name);
        model.addAttribute("team", team);

        List<Event> eventsHome = eventService.findHomeByTeamName(name);
        model.addAttribute("eventsHome", eventsHome);

        System.out.println(eventsHome.get(1).toString());

        List<Event> eventsAway = eventService.findAwayByTeamName(name);
        model.addAttribute("eventsAway", eventsAway);
        return "user/TeamView";
    }

}
