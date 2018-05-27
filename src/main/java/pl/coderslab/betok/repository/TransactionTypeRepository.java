package pl.coderslab.betok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.TransactionType;


public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

    TransactionType findById(long id);
    TransactionType findByName(String name);


}
