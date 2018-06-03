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

import java.time.LocalDate;

@Controller
public class ApiFootballController {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EventRepository eventRepository;


    @GetMapping("/admin/get-countries")
    public String getCountriesAction() {
        String url = "https://apifootball.com/api/?action=get_countries&APIkey=c4e29ebddc8c975cfd056599e5421d65d34ea41eab067765b95e776416e59cb6";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CountryDto[]> responseCountries = restTemplate.getForEntity(
                url, CountryDto[].class);
        CountryDto[] countries = responseCountries.getBody();
        for (CountryDto country: countries) {
            Country countryEntity = new Country();
            countryEntity.setId(country.getApiCountryId());
            countryEntity.setName(country.getName());

            countryRepository.save(countryEntity);
        }
        return "redirect:/admin/populate";
    }

    @GetMapping("/admin/get-leagues")
    public String getLeaguesAction() {
        String url = "https://apifootball.com/api/?action=get_leagues&APIkey=c4e29ebddc8c975cfd056599e5421d65d34ea41eab067765b95e776416e59cb6";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LeagueDto[]> responseLeagues = restTemplate.getForEntity(
                url, LeagueDto[].class);
        LeagueDto[] leagues = responseLeagues.getBody();
        for (LeagueDto league: leagues) {
            League leagueEntity = new League();
            leagueEntity.setName(league.getName());
            leagueEntity.setId(league.getApiLeagueId());

            leagueEntity.setCountry(countryRepository.findById(league.getApiCountryId()).orElse(null));
            leagueRepository.save(leagueEntity);
        }
        return "redirect:/admin/populate";
    }

    @GetMapping("/admin/get-teams/{leagueId}")
    public String getTeamsAction(@PathVariable("leagueId") int leagueId) {
        String url = "https://apifootball.com/api/?action=get_standings&league_id=" + leagueId + "&APIkey=c4e29ebddc8c975cfd056599e5421d65d34ea41eab067765b95e776416e59cb6";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TeamDto[]> responseTeams = restTemplate.getForEntity(
                url, TeamDto[].class);
        TeamDto[] teams = responseTeams.getBody();
        for (TeamDto team: teams) {
            Team teamEntity = new Team();
            teamEntity.setName(team.getName());
            BeanUtils.copyProperties(team, teamEntity);
            teamEntity.setLeague(leagueRepository.findById(team.getApiLeagueId()).orElse(null));

            teamRepository.save(teamEntity);
        }
        return "redirect:/admin/populate";
    }

    /**
     * Method for populating the database with events from API Football; set to download events from last 3 months
     * from both available leagues: Championship and Legue2. As it can take a long amount of time, it is set to use
     * multithreading with Spring @Async
     * @return
     */
    //@Async
    @RequestMapping("/admin/get-events")
    public String getMatchAction() {
        String url = "https://apifootball.com/api/?action=get_events&from=2018-03-01&to=2018-06-01&APIkey=c4e29ebddc8c975cfd056599e5421d65d34ea41eab067765b95e776416e59cb6";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<EventDto[]> responseMatch = restTemplate.getForEntity(
                url, EventDto[].class);
        EventDto[] events = responseMatch.getBody();
        for (EventDto event : events) {
            Event eventEntity = new Event();
            eventEntity.setId(event.getId());
            eventEntity.setLeague(leagueRepository.findById(event.getApiLeagueId()).orElse(null));
            eventEntity.setDate(LocalDate.parse(event.getDate()));
            eventEntity.setTime(event.getTime());
            eventEntity.setHomeTeam(teamRepository.findByName(event.getHomeTeam()));
            eventEntity.setAwayTeam(teamRepository.findByName(event.getAwayTeam()));
            eventEntity.setHomeTeamName(event.getHomeTeam());
            eventEntity.setAwayTeamName(event.getAwayTeam());

            try {
                eventEntity.setHomeGoals(Integer.parseInt(event.getHomeGoals()));
                eventEntity.setAwayGoals(Integer.parseInt(event.getAwayGoals()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                }

            eventEntity.setStatus(event.getStatus());
            eventEntity.setLive(event.getLive());
            eventRepository.save(eventEntity);
        }
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