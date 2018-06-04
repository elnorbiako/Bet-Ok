package pl.coderslab.betok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Bet;

import java.util.List;


public interface BetRepository extends JpaRepository<Bet, Long> {

    List<Bet> findByUserId(long id);

    List<Bet> findByActive(int active);

    List<Bet> findByResult(String result);


}
