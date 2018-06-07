package pl.coderslab.betok.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.repository.AccountRepository;
import pl.coderslab.betok.service.AccountService;

/**
 * Service that connects Account Repo {@link AccountRepository} and holds needed logic.
 */
@Service
public class AccountServiceImpl implements AccountService {


    final private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findByUserId(long id) {
        return accountRepository.findByUserId(id);
    }
}
