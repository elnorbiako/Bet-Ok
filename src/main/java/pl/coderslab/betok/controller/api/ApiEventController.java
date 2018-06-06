package pl.coderslab.betok.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.betok.service.ApiService;


/**
 * This is controller responsible for handling upcoming Events {@link pl.coderslab.betok.entity.Event }.
 *
 *
 *
 * @return JSON with list of all upcoming Events
 */

@RestController
@RequestMapping("/api")
public class ApiEventController {

    private final ApiService apiService;

    public ApiEventController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping(path= "/events")
    public String events() {
        return apiService.getUpcomingEvents().toString();
    }
}
