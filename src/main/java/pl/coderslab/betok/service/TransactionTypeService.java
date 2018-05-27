package pl.coderslab.betok.service;


import pl.coderslab.betok.entity.Account;
import pl.coderslab.betok.entity.TransactionType;

public interface TransactionTypeService {

    TransactionType findById(long id);

    TransactionType findByName(String name);

    long getNumTT();

    public void saveTT(String name);
}
