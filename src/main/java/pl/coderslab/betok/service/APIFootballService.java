package pl.coderslab.betok.service;


import pl.coderslab.betok.entity.Account;

public interface APIFootballService {

    public void getCountries();
    public void getLeagues();
    public void getEvents();
    public void getTeams(int leagueId);


}
