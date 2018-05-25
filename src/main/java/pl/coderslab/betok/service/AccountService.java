package pl.coderslab.betok.service;


import pl.coderslab.betok.entity.Account;

public interface AccountService {

    Account findByUserId(long id);

}
