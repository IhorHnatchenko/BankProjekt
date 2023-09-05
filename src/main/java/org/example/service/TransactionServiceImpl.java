package org.example.service;

import org.example.entities.Account;
import org.example.entities.Client;
import org.example.entities.Transaction;
import org.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final AccountService accountService;

    private final TransactionRepository transactionRepository;

    private final ClientService clientService;

    public TransactionServiceImpl(
            AccountService accountService,
            TransactionRepository transactionRepository,
            ClientService clientService) {

        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
        this.clientService = clientService;
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getById(long transactionId) {
        return transactionRepository.getReferenceById(transactionId);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }



    @Override
    public void transfer(
            long accountOneId,
            long accountTwoId,
            BigDecimal balance,
            String description)
            throws IllegalAccessException {

        Account accountOne = accountService.getById(accountOneId);
        Account accountTwo = accountService.getById(accountTwoId);

        if (accountOne.getBalance().subtract(balance).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAccessException("Not enough amount on account.");
        }

        Timestamp create_at = new Timestamp(System.currentTimeMillis());
        Transaction transaction = new Transaction(balance, accountOne, accountTwo, description, create_at);

        accountOne.setBalance(accountOne.getBalance().subtract(balance));
        accountService.save(accountOne);

        accountTwo.setBalance(accountTwo.getBalance().add(balance));
        accountService.save(accountTwo);

        transactionRepository.save(transaction);
    }
}
