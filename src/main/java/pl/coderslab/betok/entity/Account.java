package pl.coderslab.betok.entity;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * This entity represent an {@link User} account, which held {@link Transaction} list and total
 * balance.
 * In future there should be some kind of scheduled verification of integrity (sum of all transactions = balance)
 * with system alerts if sum isn't right.
 * Cash is held as a BigDecimal, due to a math inconsistent behaviour of a double type.
 */


@Entity
@Table(name = "accounts")
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal cash;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public Account() {
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

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}