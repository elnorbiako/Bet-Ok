package pl.coderslab.betok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Transaction;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.service.TransactionService;
import pl.coderslab.betok.service.UserService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
public class UserController {

    private final UserService userService;
    private final TransactionService transactionService;


    @Autowired
    public UserController(UserService userService,
                          TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }


    @GetMapping("/user/cashIn")
    public String cashIn(Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        double amount = 0.00;
        model.addAttribute("amount" , amount);
        model.addAttribute("user", user);

        return "user/CashInForm";
    }

    @GetMapping("/user/cashIn/{amount}")
    public String cashIn(@PathVariable double amount, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        transactionService.saveCashInTransaction(BigDecimal.valueOf(amount), user.getAccount());
        return "redirect:/home";
    }


    @GetMapping("/user/cashOut")
    public String cashOut(Model model) {
        double amount = 0.00;
        model.addAttribute("amount" , amount);

        return "user/CashOutForm";
    }

    @PostMapping("/user/cashOut")
    public String cashOut(Model model, @RequestParam("amount") double amount, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        BigDecimal balance = user.getAccount().getCash();
        Account account = user.getAccount();

            if (BigDecimal.valueOf(amount).compareTo(balance)>0) {
                model.addAttribute("message", "Not enough credits on account.");
            }
            else {
                transactionService.saveCashOutTransaction(BigDecimal.valueOf(amount), account);
                model.addAttribute("message", "Transfer complete.");
            }


        return "user/CashOutForm";
    }



    @GetMapping("/user/transactions")
    public String showUserTransactions(Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        model.addAttribute("transactions", transactionService.findByAccountIdOrderByCreatedDesc(user.getAccount().getId()));
        return "user/UserTransactionsView";
    }




}
