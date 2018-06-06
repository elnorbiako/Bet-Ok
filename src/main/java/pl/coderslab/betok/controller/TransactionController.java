package pl.coderslab.betok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.betok.entity.Transaction;
import pl.coderslab.betok.service.TransactionService;

@Controller
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/transaction")
    public String transctionDetail(@RequestParam(value = "id", required = true) long id, Model model) {

        Transaction transaction = transactionService.findById(id);

        model.addAttribute("transaction", transaction);

        return "user/SingleTransactionView";

    }


}
