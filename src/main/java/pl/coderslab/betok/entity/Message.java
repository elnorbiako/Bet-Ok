package pl.coderslab.betok.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Representation of a in-app Message system. Right now it is used to send system messages for transactions
 * {@link Transaction} like CashIn, CashOut, BetPlaced, BetWin. In future it can be extended to a User {@link User} to
 * user messages, and add a email sending support (as all users needs to state emails for their account).
 */
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    /**
     * Users {@link User} in message system are in relation Two to many (twice one to many)
     */
    @ManyToOne
    @JoinColumn
    private User sender;

    @ManyToOne
    @JoinColumn
    private User receiver;

    private LocalDateTime created;

    private String title;

    private String messageText;

    /**
     * Used to list messages as NEW! or already red
     */
    private boolean isRead;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
