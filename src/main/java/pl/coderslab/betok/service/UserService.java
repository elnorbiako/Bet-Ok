package pl.coderslab.betok.service;

import pl.coderslab.betok.entity.User;

import java.util.List;

public interface UserService {

    long getNumUsers();

    List<User> findAll();

    User findByUserName(String name);

    void saveUser(User user);

    void saveAdmin(User user);

}
