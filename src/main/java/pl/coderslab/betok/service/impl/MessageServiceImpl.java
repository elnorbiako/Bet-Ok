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
        return messageRepository.findAllByReceiverId(id);
    }

    @Override
    public Message findById(long id) {
        return messageRepository.findById(id);
    }

    @Override
    public void sendMessage(Message message, User sender, User receiver) {
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setCreated(LocalDateTime.now());
        message.setRead(false);
        messageRepository.save(message);
    }

    @Override
    public void receiveMessage(Message message) {
        message.setRead(true);
        messageRepository.save(message);
    }

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
