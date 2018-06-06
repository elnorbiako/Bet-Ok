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


/**
 * This is controller responsible for handling web requests regarding Bets {@link Bet}, along with critical functionality
 * of placing a bet.
 *
 */

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

    /**
     * GET for placing a bet  {@link pl.coderslab.betok.entity.Bet}.
     *
     *
     * @return Bet Form with already defined parameters: User {@link User}, Event {@link Event} and odds
     * chosen by user on EventView.
     *
     * */

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

    /**
     * POST for placing a bet  {@link pl.coderslab.betok.entity.Bet}.
     * @param eventId Event for which this particural bet will be placed
     *
     * @param odd Odd (bet type) chosen by user on Event Page.
     *
     * @param amount Amount of cash that user is willing to place on this bet. Filled in Bet form.
     *
     * @param rate Rate at which chosen odd will be calculated in case of a win
     *
     * Validations during saving (with error message):
     *             1) If amount of cash is >0
     *             2) If user have a sufficient amount of cash on account
     *             3) If event is in 'SCHEDULED' status (to prevent betting on past events).
     * @return Saves a correct bet to DB..
     *
     * */

    @PostMapping("/user/bet/{id}/{odd}")
    public String placeBet(@PathVariable(value = "id", required = true) long eventId,
                           @PathVariable(value = "odd", required = true) String odd,
                           Model model, @RequestParam("amount") double amount, @RequestParam("rate") double rate,// long eventId, String odd,
                           Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        Event event = eventService.findById(eventId);

        BigDecimal balance = user.getAccount().getCash();

        if ( !(amount>0) ) {
            model.addAttribute("message", "Error: incorrect amount.");
        }
        else if (BigDecimal.valueOf(amount).compareTo(balance)>0) {
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

    /**
     * GET for listing all bets {@link Bet} for a loggedIn user {@link User}- both active and passive (with result).
     *
     */

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
