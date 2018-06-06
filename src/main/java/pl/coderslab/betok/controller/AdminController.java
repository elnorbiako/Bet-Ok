package pl.coderslab.betok.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.service.TransactionService;
import pl.coderslab.betok.service.UserService;

import javax.validation.Valid;


/**
 * This is controller responsible for handling /administrator web requests. Its allowed only for users {@link User} with
 * added ROLE_ADMIN role {@link pl.coderslab.betok.entity.Role}.
 */
@Controller
public class AdminController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TransactionService transactionService;




    @Autowired
    public AdminController(UserService userService,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }



    /**
     * GET for adding a new user {@link User} with ADMIN_ROLE rights. Only another admin can perform this operation.
     * @return Form for creating new user with admin rights
     *
     */

    @GetMapping("/admin/addAdmin")
    public String adminForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/AdminForm";
    }

    /**
     * POST for adding a new user {@link User} with ADMIN_ROLE rights. Only another admin can perform this operation.
     * @param  user from Spring Security logged in User
     *
     */
    @PostMapping("/admin/addAdmin")
    public String adminForm(@Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "/admin/addAdmin";
        }
        User adminDb = new User();

        // Something I found on mykong to easy up the process of transfering data from similar entity (to dto fror ex.
        // If there will be some free time - learn it! 
        //BeanUtils.copyProperties();
        adminDb.setUsername(user.getUsername());
        adminDb.setFirstName(user.getFirstName());
        adminDb.setLastName(user.getLastName());
        adminDb.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        adminDb.setEmail(user.getEmail());
        adminDb.setEnabled(true);

        userService.saveAdmin(adminDb);
        return "redirect:/home";
    }

    /**
     * GET for listing all users {@link User} in DB.
     *
     */
    @GetMapping("/admin/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/UsersView";
    }

    /**
     * GET for listing last 50 transactions  {@link pl.coderslab.betok.entity.Transaction} in DB.
     * Can be easily modified to list all of them. When pagination using JS will be implemented this will be a
     * preffered way of presenting data.
     *
     */
    @GetMapping("/admin/transactions")
    public String showAllTransactions(Model model) {
        model.addAttribute("transactions", transactionService.findTop50ByOrderByCreatedDesc());
        return "admin/TransactionsView";
    }

}
