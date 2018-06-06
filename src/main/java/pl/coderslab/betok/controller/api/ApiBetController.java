package pl.coderslab.betok.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.betok.service.ApiService;

/**
 * This is controller responsible for handling API for Bets {@link pl.coderslab.betok.entity.Bet }. Based on a userId
 * {@link pl.coderslab.betok.entity.User} and bet active (0/1) it takes data and return it as JSON using {@link ApiService}.
 *
 *
 *
 * @return JSON with active/passive bets for given User {@link pl.coderslab.betok.entity.User}
 */

@RestController
@RequestMapping("/api")
public class ApiBetController {

    private final ApiService apiService;

    public ApiBetController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping(path= "/bet/{userId}/{active}")
    public String betsForUser(@PathVariable(value = "userId", required = true) long userId,
                              @PathVariable(value = "active", required = true) int active) {
        return apiService.getUserBets(userId, active).toString();
    }
}
