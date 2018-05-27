package pl.coderslab.betok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Transaction;
import pl.coderslab.betok.entity.TransactionType;
import pl.coderslab.betok.repository.TransactionRepository;
import pl.coderslab.betok.repository.TransactionTypeRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionTypeRepository transactionTypeRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findByTransactionTypeName(String name) {
        return transactionRepository.findByTransactionTypeName(name);
    }

    @Override
    public List<Transaction> findByAccountIdOrderByCreatedDesc(long id) {
        return transactionRepository.findByAccountIdOrderByCreatedDesc(id);
    }

    @Override
    public Transaction findById(long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public void saveCashInTransaction(BigDecimal amount, Account account) {
        Transaction transactionDb = new Transaction();
        transactionDb.setCreated(LocalDateTime.now());
        transactionDb.setAmount(amount);
        transactionDb.setTransactionType(transactionTypeRepository.findByName("CashIn"));
        transactionDb.setAccount(account);
        transactionRepository.save(transactionDb);
    }
}