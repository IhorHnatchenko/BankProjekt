package org.example.service;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService<T> {

    List<T> getAll();

    T getById(long transactionId);

    T save(T transaction);

    void transfer(
            long accountOneId,
            long accountTwoId,
            BigDecimal balance,
            String description
    ) throws IllegalAccessException;

}
