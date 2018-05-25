package pl.coderslab.betok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.User;


public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUserId(long id);


}
