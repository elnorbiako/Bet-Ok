package pl.coderslab.betok.entity;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representation of critical thing in betting app - bet itself.
 * Description:
 *      - User {@link User} represent owner of a bet
 *      - Amount (BigDec) represent cash that user put on that bet
 *      - Rate represents odd factor fo users type. In 'normal' conditions this will be gathered from external API.
 *      right now, due to some problems with API Football mechanism, rates are randomized at 1.01 to 4.99
 *      - Event {@link Event} to which this particular bet is connected. In future this can bet upgraded to an MultiBet,
 *      where on one bet User can add multiple events with multiple rates. Then rate is multiplied by each rate, but to
 *      win all bets in multibet must ended as WON.
 *
 */
@Entity
@Table(name = "bets")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    private LocalDateTime created;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal amount;

    private BigDecimal rate;

    @ManyToOne
    @JoinColumn
    private Event event;

    private String odd;

    //active bets (1) - before event;
    private int active;

    //result of bet - W/L
    private String result;

    public Bet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getOdd() {
        return odd;
    }

    public void setOdd(String odd) {
        this.odd = odd;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
