package pl.coderslab.betok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Transaction;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByTransactionTypeName(String name);
    List<Transaction> findByAccountIdOrderByCreatedDesc(long id);
    Transaction findById(long id);

}
