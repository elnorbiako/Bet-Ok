package pl.coderslab.betok.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.betok.dto.CountryDto;
import pl.coderslab.betok.dto.LeagueDto;
import pl.coderslab.betok.dto.TeamDto;
import pl.coderslab.betok.entity.Country;
import pl.coderslab.betok.entity.League;
import pl.coderslab.betok.entity.Team;
import pl.coderslab.betok.repository.CountryRepository;
import pl.coderslab.betok.repository.EventRepository;
import pl.coderslab.betok.repository.LeagueRepository;
import pl.coderslab.betok.repository.TeamRepository;

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
        return "redirect:/admin/panel";
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
        return "redirect:/admin/panel";
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
            teamEntity.setLeague(leagueRepository.findById(team.getApiLeagueId()).orElse(null));
            teamRepository.save(teamEntity);
        }
        return "redirect:/admin/panel";
    }

//
//    @RequestMapping("/admin/get-match")
//    public String getMatchAction() {
//        String url = "https://apifootball.com/api/?action=get_events&from=2016-10-30&to=2016-11-11&league_id=63&APIkey=c4e29ebddc8c975cfd056599e5421d65d34ea41eab067765b95e776416e59cb6";
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<MatchDto[]> responseMatch = restTemplate.getForEntity(
//                url, MatchDto[].class);
//        MatchDto[] matches = responseMatch.getBody();
//        for (MatchDto match : matches) {
//            logger.info("matches {}", match);
//            MatchDao.add(match);
//        }
//        return "some result - match";
//    }
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