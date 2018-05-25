package pl.coderslab.betok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.betok.service.UserService;

import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;



    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }




    @GetMapping("/home")
    public String home(Model model, @A) {
        return "Home";
    }
}
