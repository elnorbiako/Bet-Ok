package pl.coderslab.betok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.service.APIFootballService;


/**
 * This is controller responsible for handling web requests regarding connection to API Football
 * {@link ://apifootball.com/}.
 * Normally it would be used on a scheduled basis, but due to lack of events (pre World Cup period) it is used once for
 * populating DB with  {@link pl.coderslab.betok.entity.Country},  {@link pl.coderslab.betok.entity.League},
 *  {@link pl.coderslab.betok.entity.Team} and {@link pl.coderslab.betok.entity.Event}.
 *
 * Accessible from Admin panel.
 */
@Controller
public class ApiFootballController {


    @Autowired
    APIFootballService apiFootballService;

    /**
     * GET for adding countries {@link pl.coderslab.betok.entity.Country} to DB.
     *
     */

    @GetMapping("/admin/get-countries")
    public String getCountriesAction() {

       apiFootballService.getCountries();

        return "redirect:/admin/populate";
    }

    /**
     * GET for adding leagues {@link pl.coderslab.betok.entity.League} to DB.
     *
     */

    @GetMapping("/admin/get-leagues")
    public String getLeaguesAction() {

        apiFootballService.getLeagues();

        return "redirect:/admin/populate";
    }

    /**
     * GET for adding {@link pl.coderslab.betok.entity.Team} to DB.
     * It is required by API Football to state leagueId from which teams & standings will be presented.
     * In our free plan, we have only access to two leagues: English Championship and French Ligue2.
     *
     */

    @GetMapping("/admin/get-teams/{leagueId}")
    public String getTeamsAction(@PathVariable("leagueId") int leagueId) {
        apiFootballService.getTeams(leagueId);

        return "redirect:/admin/populate";
    }

    /**
     * GET for adding {@link pl.coderslab.betok.entity.Event} to DB.
     * From plenty of data on API Football, right now I'm using only most important ones, like teams, result, date,
     * time. In future it can be expended to use team details (like formation, players, goal scorers and so on).
     *
     */

    @RequestMapping("/admin/get-events")
    public String getMatchAction() {

        apiFootballService.getEvents();

        return "redirect:/admin/populate";
    }

    /**
     * Option for populating database with fake users using my own faker API. Currently not used (only self added
     * users are present)
     *
     */
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