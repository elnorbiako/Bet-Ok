package pl.coderslab.betok.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.betok.service.ApiService;

@RestController
@RequestMapping("/api")
public class ApiTransactionController {

    private final ApiService apiService;

    public ApiTransactionController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping(path= "/transactions")
    public String transactions() {
        return apiService.getAllTransactions().toString();
    }

    @GetMapping(path= "/transactions/{accountId}")
    public String transactionsForAccount(@PathVariable(value = "accountId", required = true) long accountId ) {
        return apiService.getUserTransaction(accountId).toString();
    }
}
