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




    @GetMapping("/admin/addAdmin")
    public String adminForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/AdminForm";
    }

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

    @GetMapping("/admin/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/UsersView";
    }

    @GetMapping("/admin/transactions")
    public String showAllTransactions(Model model) {
        model.addAttribute("transactions", transactionService.findTop50ByOrderByCreatedDesc());
        return "admin/TransactionsView";
    }

}
