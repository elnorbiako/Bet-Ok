package pl.coderslab.betok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.service.RoleService;
import pl.coderslab.betok.service.UserService;

import javax.validation.Valid;

/**
 * Controller responsible for web requests regarding {@link User} registration procedure.
 */
@Controller
public class RegisterController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;




    @Autowired
    public RegisterController(UserService userService,
                              BCryptPasswordEncoder bCryptPasswordEncoder,
                              RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }


    /**
     * GET for user registration
     * @param model Adds a empty model to form
     * @return
     */

    @GetMapping("/register")
    public String userForm(Model model) {
        model.addAttribute("user", new User());
        return "user/UserForm";
    }

    /**
     * POST for handling user registration procedure. It validates if given data is correct and saves a new user using
     * {@link UserService }. It is required that user checks 'Over 18' field to proceed.
     * @param user valid user from registration form
     * @param result for possible errors
     *
     */
    @PostMapping("/register")
    public String userForm(@Valid @ModelAttribute User user, BindingResult result) {

        if (result.hasErrors()) {
            return "user/UserForm";
        }

        User userDb = new User();

        userDb.setUsername(user.getUsername());
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        userDb.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDb.setEmail(user.getEmail());
        userDb.setEnabled(true);

        userService.saveUser(user);
        return "redirect:/home";
    }

}
