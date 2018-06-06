package pl.coderslab.betok.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Bet;
import pl.coderslab.betok.entity.Event;
import pl.coderslab.betok.entity.Transaction;
import pl.coderslab.betok.service.ApiService;
import pl.coderslab.betok.service.BetService;
import pl.coderslab.betok.service.EventService;
import pl.coderslab.betok.service.TransactionService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private final EventService eventService;
    private final TransactionService transactionService;
    private final BetService betService;

    private ArrayList<JSONObject> jsonObjects = new ArrayList<>();

    public ApiServiceImpl(EventService eventService, TransactionService transactionService, BetService betService) {
        this.eventService = eventService;
        this.transactionService = transactionService;
        this.betService = betService;
    }

    @Override
    public ArrayList<JSONObject> getUpcomingEvents() throws JSONException {
        jsonObjects.clear();
        List<Event> eventsDb = eventService.findAllScheduledEvents("SCHEDULED");

        for (Event event: eventsDb) {

            JSONObject oJsonInner = new JSONObject();
            oJsonInner.put("homeTeam", event.getHomeTeamName());
            oJsonInner.put("awayTeam", event.getAwayTeamName());
            oJsonInner.put("date", event.getDate());
            oJsonInner.put("time", event.getTime());
            oJsonInner.put("league", event.getLeague().getName());
            oJsonInner.put("odd_1", event.getOdd_1());
            oJsonInner.put("odd_X", event.getOdd_x());
            oJsonInner.put("odd_2", event.getOdd_2());
            jsonObjects.add(oJsonInner);
        }

        return jsonObjects;
    }

    @Override
    public ArrayList<JSONObject> getAllTransactions() throws JSONException {
        jsonObjects.clear();
        List<Transaction> transactions = transactionService.findAllOrderByCreatedDesc();

        for (Transaction transaction: transactions) {

            JSONObject oJsonInner = new JSONObject();
            oJsonInner.put("id", transaction.getId());
            oJsonInner.put("type", transaction.getTransactionType().getName());
            oJsonInner.put("date", transaction.getCreated());
            oJsonInner.put("account_id", transaction.getAccount().getId());
            oJsonInner.put("amount", transaction.getAmount());

            jsonObjects.add(oJsonInner);
        }

        return jsonObjects;
    }

    @Override
    public ArrayList<JSONObject> getUserTransaction(long accountId) throws JSONException {
        jsonObjects.clear();
        List<Transaction> transactions = transactionService.findByAccountIdOrderByCreatedDesc(accountId);

        for (Transaction transaction: transactions) {

            JSONObject oJsonInner = new JSONObject();
            oJsonInner.put("id", transaction.getId());
            oJsonInner.put("type", transaction.getTransactionType().getName());
            oJsonInner.put("date", transaction.getCreated());
            oJsonInner.put("amount", transaction.getAmount());

            jsonObjects.add(oJsonInner);
        }

        return jsonObjects;
    }

    @Override
    public ArrayList<JSONObject> getUserBets(long userId, int active) throws JSONException {
        jsonObjects.clear();
        List<Bet> bets = betService.findByActiveAndUserId(active, userId);

        for (Bet bet: bets) {

            JSONObject oJsonInner = new JSONObject();
            oJsonInner.put("id", bet.getId());
            oJsonInner.put("event_id", bet.getEvent().getId());
            oJsonInner.put("created", bet.getCreated());
            oJsonInner.put("amount", bet.getAmount());
            oJsonInner.put("odd", bet.getOdd());
            oJsonInner.put("rate", bet.getRate());
            oJsonInner.put("active", bet.getActive());
            oJsonInner.put("result", bet.getResult());


            jsonObjects.add(oJsonInner);
        }

        return jsonObjects;
    }
}
