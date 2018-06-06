package pl.coderslab.betok.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Message;
import pl.coderslab.betok.entity.Transaction;
import pl.coderslab.betok.entity.TransactionType;
import pl.coderslab.betok.repository.AccountRepository;
import pl.coderslab.betok.repository.TransactionRepository;
import pl.coderslab.betok.repository.TransactionTypeRepository;
import pl.coderslab.betok.service.MessageService;
import pl.coderslab.betok.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    final
    TransactionTypeRepository transactionTypeRepository;

    final
    TransactionRepository transactionRepository;

    final
    AccountRepository accountRepository;

    final
    MessageService messageService;

    @Autowired
    public TransactionServiceImpl(TransactionTypeRepository transactionTypeRepository,
                                  TransactionRepository transactionRepository,
                                  AccountRepository accountRepository,
                                  MessageService messageService) {
        this.transactionTypeRepository = transactionTypeRepository;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.messageService = messageService;
    }

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
    public List<Transaction> findTop50ByOrderByCreatedDesc() {
        return transactionRepository.findTop50ByOrderByCreatedDesc();
    }

    @Transactional
    @Override
    public void saveCashInTransaction(BigDecimal amount, Account account) {
        Transaction transactionDb = new Transaction();
        transactionDb.setCreated(LocalDateTime.now());
        transactionDb.setAmount(amount);
        transactionDb.setTransactionType(transactionTypeRepository.findByName("CashIn"));
        transactionDb.setAccount(account);
        BigDecimal current = account.getCash();
        current = current.add(amount);
        account.setCash(current);
        transactionRepository.save(transactionDb);
        accountRepository.save(account);
        Message message = new Message();
        message.setTitle("New CashIn transaction");
        message.setMessageText("Test message from System. Amount: "+ amount.toString() + " credits." );
        messageService.sendSystemMessage(message, account.getUser());

    //    System.out.println(account.getUser().getUsername());
    }

    @Transactional
    @Override
    public void saveCashOutTransaction(BigDecimal amount, Account account) {
        Transaction transactionDb = new Transaction();
        transactionDb.setCreated(LocalDateTime.now());
        transactionDb.setAmount(amount);
        transactionDb.setTransactionType(transactionTypeRepository.findByName("CashOut"));
        transactionDb.setAccount(account);
        BigDecimal current = account.getCash();
        current = current.subtract(amount);
        account.setCash(current);
        transactionRepository.save(transactionDb);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void savePlaceBetTransaction(BigDecimal amount, Account account) {
        Transaction transactionDb = new Transaction();
        transactionDb.setCreated(LocalDateTime.now());
        transactionDb.setAmount(amount);
        transactionDb.setTransactionType(transactionTypeRepository.findByName("PlaceBet"));
        transactionDb.setAccount(account);
        BigDecimal current = account.getCash();
        current = current.subtract(amount);
        account.setCash(current);
        transactionRepository.save(transactionDb);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void saveBetWinTransaction(BigDecimal amount, Account account) {
        Transaction transactionDb = new Transaction();
        transactionDb.setCreated(LocalDateTime.now());
        transactionDb.setAmount(amount);
        transactionDb.setTransactionType(transactionTypeRepository.findByName("BetWon"));
        transactionDb.setAccount(account);
        BigDecimal current = account.getCash();
        current = current.add(amount);
        account.setCash(current);
        transactionRepository.save(transactionDb);
        accountRepository.save(account);
    }

    @Override
    public List<Transaction> findAllOrderByCreatedDesc() {
        return transactionRepository.findAllByOrderByCreatedDesc();
    }
}