package pl.coderslab.betok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.betok.entity.Message;
import pl.coderslab.betok.entity.Transaction;
import pl.coderslab.betok.entity.User;
import pl.coderslab.betok.repository.MessageRepository;
import pl.coderslab.betok.service.MessageService;
import pl.coderslab.betok.service.TransactionService;
import pl.coderslab.betok.service.UserService;

import java.util.List;

@Controller
public class MessageController {

    final
    MessageService messageService;

    final
    UserService userService;



    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/user/received")
    public String receivedMessages(Model model, Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        List<Message> messages = messageService.allByReceiverId(user.getId());
        model.addAttribute("messages", messages);


        return "user/ReceivedMessagesView";

    }

    @GetMapping("/user/message")
    public String messageDetail(@RequestParam(value = "id", required = true) long id, Model model) {

        Message message = messageService.findById(id);
        message.setRead(true);
        messageService.updateMessage(message);


        model.addAttribute("message", message);

        return "user/MessageView";

    }


}
