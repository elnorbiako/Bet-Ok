package pl.coderslab.betok.service;


import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Message;
import pl.coderslab.betok.entity.User;

import java.util.List;

public interface MessageService {

    List<Message> allBySenderId(long id);

    List<Message> allByReceiverId(long id);

    Message findById(long id);

    void sendMessage(Message message, User sender, User receiver);

    void sendSystemMessage(Message message, User receiver);

    void receiveMessage(Message message);
}
