package pl.coderslab.betok.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "events")
public class Event {

    @Id
    private long id;

    private LocalDate date;

    // need to think about that - if String will be enough. Maybe a entity for that?
    // also - don't know how API shows time during live events. So i guess it will be: SCHEDULED, FT, HT and 1-90
    private String status;

    private String time;

    @ManyToOne
    @JoinColumn
    private League league;

    @ManyToOne
    @JoinColumn
    private Team homeTeam;

    private String homeTeamName;

    @ManyToOne
    @JoinColumn
    private Team awayTeam;

    private String awayTeamName;

    private int homeGoals;

    private int awayGoals;

    private int live;


    //odds to be randomized. I've tried to add 'on the fly odds from APIfootball, but due to some problems with that and
    //lack od time...
    private BigDecimal odd_1;
    private BigDecimal odd_x;
    private BigDecimal odd_2;

    //Set when event is finished. Possible: 1, X, 2. Algorithm checks goal count and determines result. Bet entity
    //will check for this info
    private String result;


    public Event() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }


    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getLive() {
        return live;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public BigDecimal getOdd_1() {
        return odd_1;
    }

    public void setOdd_1(BigDecimal odd_1) {
        this.odd_1 = odd_1;
    }

    public BigDecimal getOdd_x() {
        return odd_x;
    }

    public void setOdd_x(BigDecimal odd_x) {
        this.odd_x = odd_x;
    }

    public BigDecimal getOdd_2() {
        return odd_2;
    }

    public void setOdd_2(BigDecimal odd_2) {
        this.odd_2 = odd_2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", time='" + time + '\'' +
                ", league=" + league +
                ", homeTeam=" + homeTeam +
                ", homeTeamName='" + homeTeamName + '\'' +
                ", awayTeam=" + awayTeam +
                ", awayTeamName='" + awayTeamName + '\'' +
                ", homeGoals=" + homeGoals +
                ", awayGoals=" + awayGoals +
                ", live=" + live +
                ", odd_1=" + odd_1 +
                ", odd_x=" + odd_x +
                ", odd_2=" + odd_2 +
                '}';
    }
}
