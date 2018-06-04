package pl.coderslab.betok.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.betok.dto.CountryDto;
import pl.coderslab.betok.dto.EventDto;
import pl.coderslab.betok.dto.LeagueDto;
import pl.coderslab.betok.dto.TeamDto;
import pl.coderslab.betok.entity.Country;
import pl.coderslab.betok.entity.Event;
import pl.coderslab.betok.entity.League;
import pl.coderslab.betok.entity.Team;
import pl.coderslab.betok.repository.CountryRepository;
import pl.coderslab.betok.repository.EventRepository;
import pl.coderslab.betok.repository.LeagueRepository;
import pl.coderslab.betok.repository.TeamRepository;
import pl.coderslab.betok.service.APIFootballService;

import java.time.LocalDate;

@Controller
public class ApiFootballController {


    @Autowired
    APIFootballService apiFootballService;


    @GetMapping("/admin/get-countries")
    public String getCountriesAction() {

       apiFootballService.getCountries();

        return "redirect:/admin/populate";
    }

    @GetMapping("/admin/get-leagues")
    public String getLeaguesAction() {

        apiFootballService.getLeagues();

        return "redirect:/admin/populate";
    }

    @GetMapping("/admin/get-teams/{leagueId}")
    public String getTeamsAction(@PathVariable("leagueId") int leagueId) {
        apiFootballService.getTeams(leagueId);

        return "redirect:/admin/populate";
    }


    @RequestMapping("/admin/get-events")
    public String getMatchAction() {

        apiFootballService.getEvents();

        return "redirect:/admin/populate";
    }
//    As an option to populate database

//    @RequestMapping("/admin/get-user")
//    public String getUserAction() {
//        String url = "http://localhost:8080/api/fake-users";
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<UserDto[]> responseUser = restTemplate.getForEntity(
//                url, UserDto[].class);
//        UserDto[] users = responseUser.getBody();
//        for (UserDto user : users) {
//            logger.info("users {}", user);
//            UserDao.add(user);
//        }
//        return "some result - user";
//    }


}