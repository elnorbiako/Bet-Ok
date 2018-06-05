package pl.coderslab.betok.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.betok.entity.Transaction;
import pl.coderslab.betok.entity.TransactionType;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void findByTransactionTypeName() {

        // given
        List<Transaction> fakeTransactions = new ArrayList<>();

        Transaction fakeTransaction = new Transaction();
        fakeTransaction.setId(1L);

        TransactionType fakeTransactionType = new TransactionType();
        fakeTransactionType.setName("TransactionType1");

        TransactionType fakeTransactionType2 = new TransactionType();
        fakeTransactionType2.setName("TransactionType2");


        fakeTransaction.setTransactionType(fakeTransactionType);

        Transaction fakeTransaction2 = new Transaction();
        fakeTransaction2.setId(2L);

        fakeTransactions.add(fakeTransaction);
        fakeTransactions.add(fakeTransaction2);

        entityManager.persist(fakeTransactions);

        // when
        List <Transaction> transactions = transactionRepository.findByTransactionTypeName("TransactionType1");

        // then
        assertThat(transactions, notNullValue());
        assertTrue(transactions.contains(fakeTransaction) );
    }


    @Test
    public void findById() {
    }
}