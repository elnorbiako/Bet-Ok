package pl.coderslab.betok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.Message;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllBySenderId(long id);

    List<Message> findAllByReceiverIdOrderByCreatedDesc(long id);

    Message findById(long id);



}
