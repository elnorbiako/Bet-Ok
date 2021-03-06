package pl.coderslab.betok.entity;


import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Main entity in app. It represents both and user in therms of Spring Security (right now there are two {@link Role}:
 * User and Admin (in future this can be extended, for example: Dev for external API users, which will have access to
 * REST API section) and a actual user of the system. Each user is unique with his own account {@link Account},
 * {@link Message} sent and received, active and inactive {@link Bet}, list of favorite {@link Team}
 * and additional supporting parameters.
 *
 * In future for sure I would like to add some real payments mechanism (like PayPal, CreditCard ) to User account,
 *  maybe integrate it with some external API to handle payments procedure.
 *
 *
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "First name field is mandatory.")
    private String firstName;

    @NotBlank(message = "Last name field is mandatory.")
    private String lastName;

    @NotBlank(message = "Please provide a password.")
    private String password;

    @Email
    @NotBlank(message = "Please provide correct email address.")
    private String email;

    private boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Bet> bets;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    List<Message> messagesReceived;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    List<Message> messagesSent;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn
    private Address address;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn
    private Account account;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Team> favorites;

    @AssertTrue(message = "You must be over 18 to proceed.")
    private boolean isAdult;


    public User() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Team> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Team> favorites) {
        this.favorites = favorites;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public List<Message> getMessagesReceived() {
        return messagesReceived;
    }

    public void setMessagesReceived(List<Message> messagesReceived) {
        this.messagesReceived = messagesReceived;
    }

    public List<Message> getMessagesSent() {
        return messagesSent;
    }

    public void setMessagesSent(List<Message> messagesSent) {
        this.messagesSent = messagesSent;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }
}