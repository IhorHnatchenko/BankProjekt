package org.example.service;

import org.example.entities.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    List<Transaction> getAll();

    Transaction getById(long transactionId);

    Transaction save(Transaction transaction);

    void transfer(long accountOneId, long accountTwoId, BigDecimal balance) throws IllegalAccessException;


    // Этот метод нужно доработать с учителем.

    /* Проблема заключается в том что при попытке перевода с одного аккаунта одного пользователя
     на аккаунт другого пользователя выскакивает ошибка IllegalArgumentException.
     Это скорее всего связанно с не правильным поиском аккаунта по пользователю.*/
    void transferToAccount(
            long clientOneId,
            long accountOneId,
            long clientTwoId,
            long accountTwoId,
            BigDecimal balance) throws IllegalAccessException;
}
