package pl.coderslab.betok.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Bet;
import pl.coderslab.betok.entity.Event;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.service.BetService;
import pl.coderslab.betok.service.EventService;
import pl.coderslab.betok.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BetController {


    final private UserService userService;
    final private EventService eventService;
    final private BetService betService;


    public BetController(UserService userService, EventService eventService, BetService betService) {
        this.userService = userService;
        this.eventService = eventService;
        this.betService = betService;
    }


    @GetMapping("/user/bet/{id}/{odd}")
    public String placeBet(@PathVariable(value = "id", required = true) long eventId,
                       @PathVariable(value = "odd", required = true) String odd,
                       Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        model.addAttribute("user", user);

        Event event = eventService.findById(eventId);
        model.addAttribute("event", event);

        model.addAttribute("odd", odd);

        double rate = 0.00;

        if (odd.equals("1")){
            rate = event.getOdd_1().doubleValue();
        } else if (odd.equals("X")) {
            rate = event.getOdd_x().doubleValue();
        } else if (odd.equals("2")) {
            rate = event.getOdd_2().doubleValue();
        } else {
            // make a safety landing for error
        }

        model.addAttribute("rate", rate);
        return "/user/BetForm";
    }

    @PostMapping("/user/bet/{id}/{odd}")
    public String placeBet(@PathVariable(value = "id", required = true) long eventId,
                           @PathVariable(value = "odd", required = true) String odd,
                           Model model, @RequestParam("amount") double amount, @RequestParam("rate") double rate,// long eventId, String odd,
                           Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        Event event = eventService.findById(eventId);

        BigDecimal balance = user.getAccount().getCash();

        if (BigDecimal.valueOf(amount).compareTo(balance)>0) {
            model.addAttribute("message", "Not enough credits on account.");
        }

        else {

            if (event.getStatus().equals("SCHEDULED")) {

                Bet bet = new Bet();
                bet.setActive(1);
                bet.setEvent(event);
                bet.setOdd(odd);
                bet.setRate(BigDecimal.valueOf(rate));
                bet.setAmount(BigDecimal.valueOf(amount));
                bet.setCreated(LocalDateTime.now());
                bet.setUser(user);
                bet.setResult("0");

                betService.saveBet(bet);
                model.addAttribute("message", "Bet complete. Good luck!");

            } else {

                model.addAttribute("message", "Too late, event inactive");
            }
        }



        return "/user/BetConfirm";
    }

    @GetMapping("/user/bets")
    public String betsView(Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        model.addAttribute(user);

        List<Bet> activeBets = betService.findByActiveAndUserId(1, user.getId());
        model.addAttribute("activeBets", activeBets);

        List<Bet> inactiveBets = betService.findByActiveAndUserId(0, user.getId());
        model.addAttribute("inactiveBets", inactiveBets);

        return "user/BetsView";
    }


}
