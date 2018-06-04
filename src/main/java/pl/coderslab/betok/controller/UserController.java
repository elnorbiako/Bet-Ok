package pl.coderslab.betok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.betok.entity.*;
import pl.coderslab.betok.service.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final TransactionService transactionService;
    private final TeamService teamService;
    private final EventService eventService;
    private final BetService betService;


    @Autowired
    public UserController(UserService userService,
                          TransactionService transactionService, TeamService teamService, EventService eventService, BetService betService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.teamService = teamService;
        this.eventService = eventService;
        this.betService = betService;
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



    @GetMapping("user/favorites")
    public String favoritesView(Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        List<Team> favorites = user.getFavorites();

        model.addAttribute("favorites", favorites);
        model.addAttribute("user", user);

        return "user/FavoritesView";
    }

    @GetMapping("user/addToFav")
    public String addToFavorities(@RequestParam(value = "name", required = true) String name, Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        Team team = teamService.findByName(name);

        List<Team> favorites = user.getFavorites();
        favorites.add(team);
        user.setFavorites(favorites);

        userService.updateUser(user);

        model.addAttribute("favorites", favorites);
        model.addAttribute("user", user);

        return "user/FavoritesView";
    }

    @GetMapping("user/remFromFav")
    public String removeFromFavorities(@RequestParam(value = "name", required = true) String name, Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        Team team = teamService.findByName(name);

        List<Team> favorites = user.getFavorites();
        favorites.remove(team);
        user.setFavorites(favorites);

        userService.updateUser(user);

        model.addAttribute("favorites", favorites);
        model.addAttribute("user", user);

        return "user/FavoritesView";
    }


    @GetMapping("/user/lastEvents")
    public String lastEvents(Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        model.addAttribute(user);

        List<Event> events = eventService.find10LastEvents("FT");
        model.addAttribute("events", events);
        return "user/LastMatchesView";
    }



}
