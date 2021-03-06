package pl.coderslab.betok.service;


import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Transaction;
import pl.coderslab.betok.entity.TransactionType;
import pl.coderslab.betok.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    List<Transaction> findByTransactionTypeName(String name);
    List<Transaction> findByAccountIdOrderByCreatedDesc(long id);
    Transaction findById(long id);

    List<Transaction> findTop50ByOrderByCreatedDesc();
    List<Transaction> findAllOrderByCreatedDesc();

    void saveCashInTransaction(BigDecimal amount, Account account, User user);
    void saveCashOutTransaction(BigDecimal amount, Account account, User user);

    @Transactional
    void savePlaceBetTransaction(BigDecimal amount, Account account, User user);

    @Transactional
    void saveBetWinTransaction(BigDecimal amount, Account account, User user);
}
