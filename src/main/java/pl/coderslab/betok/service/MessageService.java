package pl.coderslab.betok.service;


import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Message;

import java.util.List;

public interface MessageService {

    List<Message> allBySenderId(long id);

    List<Message> allByReceiverId(long id);

    Message findById(long id);

    void sendMessage(Message message);

    void receiveMessage(Message message);
}
