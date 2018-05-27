package pl.coderslab.betok.service;


import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Transaction;
import pl.coderslab.betok.entity.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    List<Transaction> findByTransactionTypeName(String name);
    List<Transaction> findByAccountIdOrderByCreatedDesc(long id);
    Transaction findById(long id);

    void saveCashInTransaction(BigDecimal amount, Account account);
}
