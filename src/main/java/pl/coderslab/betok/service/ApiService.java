package pl.coderslab.betok.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public interface ApiService {


    ArrayList<JSONObject> getUpcomingEvents() throws JSONException;

    ArrayList<JSONObject> getAllTransactions() throws JSONException;

    ArrayList<JSONObject> getUserTransaction(long accountId) throws JSONException;

    ArrayList<JSONObject> getUserBets(long userId, int active) throws JSONException;
}
