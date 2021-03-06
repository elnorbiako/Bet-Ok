package pl.coderslab.betok.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Role;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.repository.UserRepository;
import pl.coderslab.betok.service.UserService;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service for handling logic for {@link User} entity.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_USER_ROLE_NAME = "ROLE_USER";
    private static final String ADMIN_ROLE_NAME = "ROLE_ADMIN";

    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EntityManager em;

    public UserServiceImpl(UserRepository userRepository,
                           RoleServiceImpl roleService,
                           BCryptPasswordEncoder passwordEncoder, EntityManager em) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.em = em;
    }

    @Override
    public long getNumUsers() {
        return userRepository.count();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * SaveUser method, used mostly when registering a new User.
     *      Procedure:
     *      1) Takes a valid user from UserForm
     *      2) Hashes users password using a {@link BCryptPasswordEncoder} mechanism
     *      3) Sets a User role to new user
     *      4) Creates new account with 0 founds; Can be easly switched to some other amount (for ex. 200 bonus for
     *      registration) - will need a validation during cashout
     *      5) Saves new user using {@link UserRepository}
     *
     * @param user new user from UserForm POST
     */
    @Override
    public void saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        Role role = roleService.getOrCreate(DEFAULT_USER_ROLE_NAME);
        Set<Role> roles = new HashSet<>(Collections.singletonList(role));
        user.setRoles(roles);

        Account account = new Account();
        BigDecimal bd = new BigDecimal("0.00");
        account.setCash(bd);
        user.setAccount(account);

        userRepository.save(user);
    }

    /**
     * SaveAdmin method, used mostly when registering a new Admin. Operation available only for other admin.
     *      Procedure:
     *      1) Takes a valid user from AdminForm
     *      2) Hashes users password using a {@link BCryptPasswordEncoder} mechanism
     *      3) Sets a User role to new user & Admin
     *      4) Creates new account with 0 founds; Can be easly switched to some other amount (for ex. 200 bonus for
     *      registration) - will need a validation during cashout
     *      5) Saves new admin using {@link UserRepository}
     *
     * @param user new admin from AdminForm POST
     */

    @Override
    public void saveAdmin(User user) {

      //  user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        Role roleA = roleService.getOrCreate(ADMIN_ROLE_NAME);
        Role roleU = roleService.getOrCreate(DEFAULT_USER_ROLE_NAME);
        Set<Role> roles = new HashSet<>();
        roles.add(roleA);
        roles.add(roleU);
        user.setRoles(roles);

        Account account = new Account();
        BigDecimal bd = new BigDecimal("0.00");
        account.setCash(bd);
        user.setAccount(account);

        userRepository.save(user);
    }


    @Override
    public User getLoggedUser(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        return user;
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }
}
