package pl.coderslab.betok.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Message;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.repository.MessageRepository;
import pl.coderslab.betok.service.MessageService;
import pl.coderslab.betok.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for handling in-app Message System.
 *
 * In future it can handle User to User {@link User} communication and be extended with email sending support
 */
@Service
public class MessageServiceImpl implements MessageService {

    final MessageRepository messageRepository;
    final UserService userService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }


    @Override
    public List<Message> allBySenderId(long id) {
        return messageRepository.findAllBySenderId(id);
    }

    @Override
    public List<Message> allByReceiverId(long id) {
        return messageRepository.findAllByReceiverIdOrderByCreatedDesc(id);
    }

    @Override
    public Message findById(long id) {
        return messageRepository.findById(id);
    }

    /**
     * Method for sending message. Can be used for user communication with admins or other users.
     * Can be extended with email option (checker with 'send also an email').
     * When sending it sets created to now() and isRead to false (so NEW! message).
     *
     * @param message Message object with filled Title and Text
     * @param sender currently loggedIn user
     * @param receiver user chosen from the list, or from search engine, or from friends group (not yet implemented)
     */
    @Override
    public void sendMessage(Message message, User sender, User receiver) {
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setCreated(LocalDateTime.now());
        message.setRead(false);
        messageRepository.save(message);
    }

    /**
     * Method for reading message. If user clicks 'Read' button to read details, ifRead is set to true (no longer
     * will be marked as a NEW! in messages view)
     * @param message Current message
     */
    @Override
    public void receiveMessage(Message message) {
        message.setRead(true);
        messageRepository.save(message);
    }

    /**
     * sendMessage method, bu instead of setting a sender as a loggedIn user, it uses a user 'system'. used for sending
     * in-app communications (confirmation of CashIn, CashOut, PlaceBet, BetWin).
     * Can be extended further (communication for settings changes, updating details, informing about Events that have
     * a team marked as Favorite, and so on.
     * @param message Message object with filled Title and Text
     * @param receiver Usually a user that is a part of that operation (for ex. cashIn), or a BetVerification.
     */
    @Override
    public void sendSystemMessage(Message message, User receiver) {
        message.setSender(userService.findByUserName("system"));
        message.setReceiver(receiver);
        message.setCreated(LocalDateTime.now());
        message.setRead(false);
        messageRepository.save(message);
    }

    @Override
    public void updateMessage(Message message) {
        messageRepository.save(message);
    }
}
