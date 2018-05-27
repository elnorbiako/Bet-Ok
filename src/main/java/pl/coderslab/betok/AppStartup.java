package pl.coderslab.betok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.coderslab.betok.entity.Role;
import pl.coderslab.betok.entity.TransactionType;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.service.RoleService;
import pl.coderslab.betok.service.TransactionTypeService;
import pl.coderslab.betok.service.UserService;


import java.util.HashSet;
import java.util.Set;

@Component
public class AppStartup implements ApplicationRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final TransactionTypeService transactionTypeService;


    @Autowired
    public AppStartup(UserService userService,
                      RoleService roleService,
                      TransactionTypeService transactionTypeService) {
        this.userService = userService;
        this.roleService = roleService;
        this.transactionTypeService = transactionTypeService;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userService.getNumUsers() == 0L) {
            userService.saveUser(testUser1());
            userService.saveUser(testUser2());
        }
        if (transactionTypeService.getNumTT() == 0L) {
            transactionTypeService.saveTT("CashIn");
            transactionTypeService.saveTT("CashOut");
        }
    }

    private User testUser1() {
        User user = new User();
        user.setUsername("alice");
        user.setPassword("zxc123");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.getOrCreate("ROLE_USER"));
        user.setRoles(userRoles);

        return user;
    }

    private User testUser2() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.getOrCreate("ROLE_ADMIN"));
        user.setRoles(userRoles);
        return user;
    }
}
