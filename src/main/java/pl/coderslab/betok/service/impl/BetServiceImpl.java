package pl.coderslab.betok.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Bet;
import pl.coderslab.betok.repository.BetRepository;
import pl.coderslab.betok.service.BetService;
import pl.coderslab.betok.service.TransactionService;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

/**
 * Service that covers all the logic behind bets {@link Bet}. Critical method is a betVerificator, which constantly checks
 * if a Event {@link pl.coderslab.betok.entity.Event} connected to bet changed its state from 'SCHEDULED' to 'FT (when I'll
 * add live matches support, there will be additional statuses on the way) and then verifies bet type to match result
 */
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

    /**
     * When bet is saved (confirmed by the user), there is also saved a transaction {@link pl.coderslab.betok.entity.Transaction}
     * PlaceBet for that, for accounting purposes. Also System message is sent to user with confirmation
     * @param bet from bet creation Form
     */
    @Override
    public void saveBet(Bet bet) {

        transactionService.savePlaceBetTransaction(bet.getAmount(), bet.getUser().getAccount(), bet.getUser());

        betRepository.save(bet);

    }

    @Override
    public List<Bet> findByActive(int active) {
        return betRepository.findAllByActive(active);
    }

    /**
     * Critical method for verification on placed bets. At scheduled rate it checks if liked Event
     * {@link pl.coderslab.betok.entity.Event} has changed its status from 'SCHEDULED' to 'FT'. If yes- it changes
     * active parameter to 0, and then checks if Bet {@link Bet} type matches Event result (which is determined by
     * EventService {@link EventServiceImpl}. If Yes - bet win amount is calculated (amount * rate), TransactionService
     * {@link TransactionServiceImpl} saves BetWin transaction along with sending message informing about win.
     * Then bet is saved as a inactive one, to be checked in User MyBets view.
     * In case of loosing - bet is saved with L result. There can be added a transaction task here, which transfers cash
     * to central account of system income :)
     */
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
                    /**
                     * There can be System message added here, with 'Lost' info, but from psychological point of view-
                     * better not :)
                     */
                }
            }

        }
    }

    /**
     * Support method for updating bet status without saving transaction and sending message.
     * @param bet
     */
    @Override
    public void updateBet(Bet bet) {
        em.merge(bet);
    }
}
