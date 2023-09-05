package org.example.service;

import org.example.entities.Transaction;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface TransactionService {

    List<Transaction> getAll();

    Transaction getById(long transactionId);

    Transaction save(Transaction transaction);

    void transfer(
            long accountOneId,
            long accountTwoId,
            BigDecimal balance,
            String description
    ) throws IllegalAccessException;

}
