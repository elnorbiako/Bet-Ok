package pl.coderslab.betok.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.betok.service.ApiService;


/**
 * This is controller responsible for handling API for Transactions {@link pl.coderslab.betok.entity.Transaction }.
 * It can be used for.ex. to integrate betting system with external accounting service.
 */
@RestController
@RequestMapping("/api")
public class ApiTransactionController {

    private final ApiService apiService;

    public ApiTransactionController(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * No parameter GET method.
     *
     * @return JSON list of all transactions {@link pl.coderslab.betok.entity.Transaction}
     */

    @GetMapping(path= "/transactions")
    public String transactions() {
        return apiService.getAllTransactions().toString();
    }

    /**
     * Transaction GET method with additional 'accountId' param.
     *
     * @return JSON list of transactions {@link pl.coderslab.betok.entity.Transaction} for a certain account
     * {@link pl.coderslab.betok.entity.Account}, which is linked OneToOne to user {@link pl.coderslab.betok.entity.User}
     */
    @GetMapping(path= "/transactions/{accountId}")
    public String transactionsForAccount(@PathVariable(value = "accountId", required = true) long accountId ) {
        return apiService.getUserTransaction(accountId).toString();
    }
}
