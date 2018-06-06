package pl.coderslab.betok.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.repository.UserRepository;

import javax.persistence.EntityManager;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private UserService userService;
    private UserRepository userRepository;
    private RoleServiceImpl roleService;
    private BCryptPasswordEncoder passwordEncoder;
    private EntityManager em;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        roleService = mock(RoleServiceImpl.class);
        passwordEncoder = mock(BCryptPasswordEncoder.class);
        em = mock(EntityManager.class);
        userService = new UserServiceImpl(userRepository, roleService, passwordEncoder, em);
    }

    @Test
    public void findAll() {
    }

    @Test
    public void shouldAddUser() {

        User testUser = new User();
        testUser.setId(123432L);
        testUser.setUsername("Tester");
        testUser.setFirstName("Tester");
        testUser.setLastName("Tester");
        testUser.setPassword(passwordEncoder.encode("Tester"));
        testUser.setEmail("tester@tester.pl");

        when(userRepository.save(testUser)).thenReturn(testUser);
        when(userRepository.findByUsername("Tester")).thenReturn(testUser);


        userService.saveUser(testUser);
        User user = userService.findByUserName("Tester");
        assertThat(user, notNullValue());
        assertThat(user.getFirstName(), is("Tester"));
    }
//  Tests to be added later on
//    @Test
//    public void findByUserName() {
//    }
//
//    @Test
//    public void saveAdmin() {
//    }
//
//    @Test
//    public void getLoggedUser() {
//    }
//
//    @Test
//    public void updateUser() {
//    }
}