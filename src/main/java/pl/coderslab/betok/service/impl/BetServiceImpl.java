package pl.coderslab.betok.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Bet;
import pl.coderslab.betok.repository.BetRepository;
import pl.coderslab.betok.service.BetService;
import pl.coderslab.betok.service.TransactionService;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

@Service
public class BetServiceImpl implements BetService {


    private final BetRepository betRepository;
    private final TransactionService transactionService;
    private final EntityManager em;

    public BetServiceImpl(BetRepository betRepository, TransactionService transactionService, EntityManager em) {
        this.betRepository = betRepository;
        this.transactionService = transactionService;
        this.em = em;
    }

    @Override
    public List<Bet> findByUserId(long id) {
        return betRepository.findByUserId(id);
    }

    @Override
    public List<Bet> findByActiveAndUserId(int active, long id) {
        return betRepository.findByActiveAndUserId(active, id);
    }

    @Override
    public List<Bet> findByResult(String result) {
        return betRepository.findByResult(result);
    }

    @Override
    public void saveBet(Bet bet) {

        transactionService.savePlaceBetTransaction(bet.getAmount(), bet.getUser().getAccount(), bet.getUser());

        betRepository.save(bet);

    }

    @Override
    public List<Bet> findByActive(int active) {
        return betRepository.findAllByActive(active);
    }

    @Override
    public void betVerificator() {

        List<Bet> activeBets = findByActive(1);

        for (Bet bet: activeBets) {

            if(bet.getEvent().getStatus().equals("FT")) {
                bet.setActive(0);
                if (bet.getOdd().equals(bet.getEvent().getResult() ) )  {
                    bet.setResult("W");
                    BigDecimal win = bet.getAmount().multiply(bet.getRate());
                    transactionService.saveBetWinTransaction(win, bet.getUser().getAccount(), bet.getUser());
                    betRepository.save(bet);
                } else {
                    bet.setResult("L");
                    betRepository.save(bet);
                }
            }

        }
    }

    @Override
    public void updateBet(Bet bet) {
        em.merge(bet);
    }
}
