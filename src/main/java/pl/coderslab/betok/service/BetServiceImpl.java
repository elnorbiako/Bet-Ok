package pl.coderslab.betok.service;

import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Bet;
import pl.coderslab.betok.repository.BetRepository;

import java.util.List;

@Service
public class BetServiceImpl implements BetService {


    private final BetRepository betRepository;

    public BetServiceImpl(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @Override
    public List<Bet> findByUserId(long id) {
        return betRepository.findByUserId(id);
    }

    @Override
    public List<Bet> findByActive(int active) {
        return betRepository.findByActive(active);
    }

    @Override
    public List<Bet> findByResult(String result) {
        return betRepository.findByResult(result);
    }

    @Override
    public void saveBet(Bet bet) {

        // all logic with that
        betRepository.save(bet);

    }
}
