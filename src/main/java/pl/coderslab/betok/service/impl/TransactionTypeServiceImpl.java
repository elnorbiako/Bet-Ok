package pl.coderslab.betok.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.betok.entity.TransactionType;
import pl.coderslab.betok.repository.TransactionTypeRepository;
import pl.coderslab.betok.service.MessageService;
import pl.coderslab.betok.service.TransactionTypeService;

@Service
public class TransactionTypeServiceImpl implements TransactionTypeService {

    final
    TransactionTypeRepository transactionTypeRepository;

    final
    MessageService messageService;

    @Autowired
    public TransactionTypeServiceImpl(TransactionTypeRepository transactionTypeRepository, MessageService messageService) {
        this.transactionTypeRepository = transactionTypeRepository;
        this.messageService = messageService;
    }

    @Override
    public TransactionType findById(long id) {
        return transactionTypeRepository.findById(id);
    }

    @Override
    public long getNumTT() {
        return transactionTypeRepository.count();

    }

    @Override
    public void saveTT(String name) {
        TransactionType tt = new TransactionType();
        tt.setName(name);
        transactionTypeRepository.save(tt);
    }

    @Override
    public TransactionType findByName(String name) {
        return transactionTypeRepository.findByName(name);
    }
}
