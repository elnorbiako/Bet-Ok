package pl.coderslab.betok.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.repository.AccountRepository;
import pl.coderslab.betok.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account findByUserId(long id) {
        return accountRepository.findByUserId(id);
    }
}
