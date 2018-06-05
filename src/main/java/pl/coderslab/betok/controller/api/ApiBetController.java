package pl.coderslab.betok.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.betok.service.ApiService;

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
