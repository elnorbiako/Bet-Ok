package pl.coderslab.betok.service;

import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Bet;
import pl.coderslab.betok.entity.Transaction;
import pl.coderslab.betok.repository.BetRepository;

import java.util.List;

@Service
public class BetServiceImpl implements BetService {


    private final BetRepository betRepository;
    private final TransactionService transactionService;

    public BetServiceImpl(BetRepository betRepository, TransactionService transactionService) {
        this.betRepository = betRepository;
        this.transactionService = transactionService;
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

        transactionService.savePlaceBetTransaction(bet.getAmount(), bet.getUser().getAccount());

        betRepository.save(bet);

    }
}
