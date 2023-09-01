package org.example.service;

import org.example.entities.Account;
import org.example.entities.Client;
import org.example.entities.Transaction;
import org.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
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
    public void transfer(long accountOneId, long accountTwoId, BigDecimal balance) throws IllegalAccessException {
        Account accountOne = accountService.getById(accountOneId);
        Account accountTwo = accountService.getById(accountTwoId);

        if (accountOne.getBalance().subtract(balance).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAccessException("Not enough amount on account.");
        }

/*        String description = accountOne.getName() +
                ": " +
                accountOne.getBalance() +
                " -> " +
                balance +
                " -> " +
                accountTwo.getName() +
                ": " +
                accountTwo.getBalance();*/

        Transaction transaction = new Transaction(balance, accountOne, accountTwo);

        accountOne.setBalance(accountOne.getBalance().subtract(balance));
        accountService.save(accountOne);


        accountTwo.setBalance(accountTwo.getBalance().add(balance));
        accountService.save(accountTwo);

        transactionRepository.save(transaction);
    }

    // Это по идее не нужно.
    @Override
    public void transferToAccount(long clientOneId, long clientTwoId, long accountOneId,long accountTwoId, BigDecimal balance) throws IllegalAccessException {
        Client clientOne = clientService.getById(clientTwoId);
        Client clientTwo = clientService.getById(clientTwoId);


/*        if (clientOne == null || clientTwo == null) {
            throw new IllegalArgumentException("Invalid client information.");
        }*/

        List<Account> clientOneAccounts = clientOne.getAccounts();
        List<Account> clientTwoAccounts = clientTwo.getAccounts();

        Account accountOne = clientOneAccounts.stream().filter(account -> account.getId() == accountOneId).findFirst().orElse(null);
        Account accountTwo = clientTwoAccounts.stream().filter(account -> account.getId() == accountTwoId).findFirst().orElse(null);


        // Он ловит здесь ошибку и я не знаю что с этим сделать.
        if (accountOne == null || accountTwo == null) {
            throw new IllegalArgumentException("Invalid account information.");
        }

        if (accountOne.getBalance().subtract(balance).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAccessException("Not enough amount on account.");
        }

        accountOne.setBalance(accountOne.getBalance().subtract(balance));
        accountService.save(accountOne);

        accountTwo.setBalance(accountTwo.getBalance().add(balance));
        accountService.save(accountTwo);
    }
}
