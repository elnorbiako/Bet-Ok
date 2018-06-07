package pl.coderslab.betok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.betok.entity.*;
import pl.coderslab.betok.service.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


/**
 * This is controller responsible for handling /user web requests. Its allowed for {@link User} with
 * both ROLE_USER and ROLE_ADMIN  {@link pl.coderslab.betok.entity.Role}. It handles most operations user can
 * request in the system.
 */
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

    /**
     * GET method for CashIn operation. Curretly not used - its a base for CashIN using POST method, along with
     * planned functionality ov validating CreditCard.
     *
     * @param authentication used for determining currently loggedIn user
     * @return
     */

    @GetMapping("/user/cashIn")
    public String cashIn(Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        double amount = 0.00;
        model.addAttribute("amount" , amount);
        model.addAttribute("user", user);

        return "user/CashInForm";
    }

    /**
     * GET method for buying credits using PathVariable. In later versions will be changed to POST method with
     * Credit Card validation and possible financial/wire transfer API
     * @param amount Amount of cash user want to add to his account
     * @param authentication used for determining currently loggedIn user
     * @return saves CashIn transaction (along with confirmation message)
     */
    @GetMapping("/user/cashIn/{amount}")
    public String cashIn(@PathVariable double amount, Authentication authentication, Model model) {
        User user = userService.getLoggedUser(authentication);

        if (!(amount > 0)) {
            model.addAttribute("message", "Error: incorrect amount.");
            return "user/CashInForm";}

            transactionService.saveCashInTransaction(BigDecimal.valueOf(amount), user.getAccount(), user);
            return "redirect:/home";
    }


    /**
     * GET for cash withdrawal
     * @param model sets an empty amount for CashOut form
     * @return
     */
    @GetMapping("/user/cashOut")
    public String cashOut(Model model) {
        double amount = 0.00;
        model.addAttribute("amount" , amount);

        return "user/CashOutForm";
    }

    /**
     * POST for money withdrawal. It verify:
     *      1)  If amount is >0
     *      2)  If user have sufficient balance  for such amount
     *
     *
     * @param amount Amount of cash to be transfered from account
     * @param authentication used for determining currently loggedIn user
     * @return saves CashOut transaction along with System Message
     */
    @PostMapping("/user/cashOut")
    public String cashOut(Model model, @RequestParam("amount") double amount, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        BigDecimal balance = user.getAccount().getCash();
        Account account = user.getAccount();

            if ( !(amount>0) ) {
                model.addAttribute("message", "Error: incorrect amount.");
            }
            else if (BigDecimal.valueOf(amount).compareTo(balance)>0) {
                model.addAttribute("message", "Not enough credits on account.");
            }
            else {
                transactionService.saveCashOutTransaction(BigDecimal.valueOf(amount), account, user);
                model.addAttribute("message", "Transfer complete.");
            }


        return "user/CashOutForm";
    }

    /**
     * GET for listing all transactions for loggedIn user
     *
     * @param authentication used for determining currently loggedIn user
     * @return
     */

    @GetMapping("/user/transactions")
    public String showUserTransactions(Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        model.addAttribute("transactions", transactionService.findByAccountIdOrderByCreatedDesc(user.getAccount().getId()));
        return "user/UserTransactionsView";
    }

    /**
     * GET for listing all Favorite teams for loggedIn user
     *
     * @param authentication used for determining currently loggedIn user
     * @return
     */

    @GetMapping("user/favorites")
    public String favoritesView(Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        List<Team> favorites = user.getFavorites();

        model.addAttribute("favorites", favorites);
        model.addAttribute("user", user);

        return "user/FavoritesView";
    }

    /**
     * GET for adding a {@link Team} to favorites list for loggedIn user
     *
     *  Verification if such team isn't on the list already. If yes - it simply redirects to Fav list without adding.
     * @param authentication used for determining currently loggedIn user
     * @return Saves current Fav list to loggedIn user
     */
    @GetMapping("user/addToFav")
    public String addToFavorities(@RequestParam(value = "name", required = true) String name, Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        Team team = teamService.findByName(name);

        List<Team> favorites = user.getFavorites();

        if(favorites.contains(team)) {
            return "redirect:/user/favorites";
        }

        favorites.add(team);
        user.setFavorites(favorites);

        userService.updateUser(user);

        model.addAttribute("favorites", favorites);
        model.addAttribute("user", user);

        return "user/FavoritesView";
    }

    /**
     * GET for removing team from Fav list
     * @param name Team name to be removed,
     *
     **/

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

    /**
     * GET for listing last 10 {@link Event} with 'FT' status - so the last ones that ended
     *
     */
    @GetMapping("/user/lastEvents")
    public String lastEvents(Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        model.addAttribute(user);

        List<Event> events = eventService.find10LastEvents("FT");
        model.addAttribute("events", events);
        return "user/LastMatchesView";
    }

    /**
     * GET for editing currently loggedIn User details
     *
     *
     */
    @GetMapping("/user/edit")
    public String userEdit(Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        model.addAttribute(user);

        return "user/UserEditForm";
    }

    /**
     * POST for saving edited User to DB.
     * @param user Validated user, during edition he can change firstname, last name, and email address
     * @param result For possible errors
     * @param authentication For getting loggedIn user

     */
    @PostMapping("/user/edit")
    public String userEdit(@ModelAttribute User user, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return "user/UserEditForm";
        }
        User userDb = userService.getLoggedUser(authentication);
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        userDb.setEmail(user.getEmail());

        userService.updateUser(userDb);

        return "redirect:/home";
    }

    @GetMapping("/user/addAddress")
    public String userAddAddress(Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        model.addAttribute(user);

        Address address = new Address();
        model.addAttribute("address", address);

        return "user/AddressForm";
    }

    @PostMapping("/user/addAddress")
    public String userAddAddress(@ModelAttribute Address address, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return "user/UserEditForm";
        }
        User userDb = userService.getLoggedUser(authentication);
        userDb.setAddress(address);

        userService.updateUser(userDb);

        return "redirect:/home";
    }

}
