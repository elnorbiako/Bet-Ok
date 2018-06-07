package pl.coderslab.betok.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.betok.entity.*;
import pl.coderslab.betok.repository.AccountRepository;
import pl.coderslab.betok.repository.TransactionRepository;
import pl.coderslab.betok.repository.TransactionTypeRepository;
import pl.coderslab.betok.service.MessageService;
import pl.coderslab.betok.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service responsible for handling Transaction {@link Transaction} logic. It simulates in-app accounting system.
 * There are four main transactions: CashIn, CashOut, PlaceBet, BetWin.
 */

@Service
public class TransactionServiceImpl implements TransactionService {

    final private TransactionTypeRepository transactionTypeRepository;

    final private TransactionRepository transactionRepository;

    final private AccountRepository accountRepository;

    final private MessageService messageService;

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

    /**
     * Method for CashIn transaction - so when user {@link User} want to add money to his account {@link Account}.
     * Procedure:
     *      1) sets created to now().
     *      2) updates account balance for loggedIn User
     *      3) sets transactionType to 'CashIn'
     *      4) saves both account and transaction
     *      5) using messageService {@link MessageServiceImpl} it sends a system message with info about transaction.
     * @param amount amount from Cash In form, how much money users wants to add to account
     * @param account account for a loggedIn user (in theory its a separate param to allow adding money not to own account,
     *                but also another (give cash to a friend, or system adding money as a bonus)
     * @param user currently loggedIn user
     */
    @Transactional
    @Override
    public void saveCashInTransaction(BigDecimal amount, Account account, User user) {
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
        messageService.sendSystemMessage(message, user);



    }
    /**
     * Method for CashOut transaction - so when user {@link User} wants to withdraw money from his account {@link Account}.
     * Procedure:
     *      1) sets created to now().
     *      2) updates account balance for loggedIn User
     *      3) sets transactionType to 'CashOut'
     *      4) saves both account and transaction
     *      5) using messageService {@link MessageServiceImpl} it sends a system message with info about transaction.
     * @param amount amount from POST CashOut form, how much money users wants to withdraw
     * @param account account for a loggedIn user
     * @param user currently loggedIn user
     */
    @Transactional
    @Override
    public void saveCashOutTransaction(BigDecimal amount, Account account, User user) {
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
        Message message = new Message();
        message.setTitle("New CashOut transaction");
        message.setMessageText("Test message from System. Amount: "+ amount.toString() + " credits." );
        messageService.sendSystemMessage(message, user);
    }

    /**
     * Similar to CashOut transaction, but amount is transfered to placed Bet {@link Bet}
     * @param amount amount of cash invested in particular Bet
     * @param account user account, separate for future implementation of a group bet
     * @param user loggedIn user
     */
    @Transactional
    @Override
    public void savePlaceBetTransaction(BigDecimal amount, Account account, User user) {
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
        Message message = new Message();
        message.setTitle("New Bet placed");
        message.setMessageText("Test message from System. Amount: "+ amount.toString() + " credits." );
        messageService.sendSystemMessage(message, user);
    }

    /**
     * Similar to CashIn transaction, it transfers Bet prize (rate*amount) to Users account with a confirm message from
     * system.
     * @param amount bet prize
     * @param account users account (in future Group bet this will be a separate support account)
     * @param user Bet owner
     */

    @Transactional
    @Override
    public void saveBetWinTransaction(BigDecimal amount, Account account, User user) {
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
        Message message = new Message();
        message.setTitle("Congratulations! You won!");
        message.setMessageText("Test message from System. Amount: "+ amount.toString() + " credits." );
        messageService.sendSystemMessage(message, user);
    }

    @Override
    public List<Transaction> findAllOrderByCreatedDesc() {
        return transactionRepository.findAllByOrderByCreatedDesc();
    }
}