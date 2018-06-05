package pl.coderslab.betok.service;

import pl.coderslab.betok.entity.Bet;

import java.util.List;

public interface BetService {

        List<Bet> findByActive(int active);
        List<Bet> findByUserId(long id);

        List<Bet> findByActiveAndUserId(int active, long id);

        List<Bet> findByResult(String result);

        void saveBet(Bet bet);

        void betVerificator();


}
