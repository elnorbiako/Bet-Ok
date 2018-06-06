package pl.coderslab.betok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Message;
import pl.coderslab.betok.repository.AccountRepository;
import pl.coderslab.betok.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    final
    MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
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
    public void sendMessage(Message message) {
        message.setCreated(LocalDateTime.now());
        message.setRead(false);
        messageRepository.save(message);
    }

    @Override
    public void receiveMessage(Message message) {
        message.setRead(true);
        messageRepository.save(message);
    }
}
