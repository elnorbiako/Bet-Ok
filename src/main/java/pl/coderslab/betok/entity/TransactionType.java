package pl.coderslab.betok.entity;


import javax.persistence.*;

/**
 * Type of transaction. Right now there are four basic ones: CashIn, CashOut, PlaceBet, BetWon
 */
@Entity
@Table(name = "transaction_types")
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public TransactionType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
