package org.example.service;

import org.example.entities.Account;
import org.example.entities.Client;
import org.example.enums.Status;
import org.example.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService<Account> {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;

    private final ClientService<Client> clientService;

    public AccountServiceImpl(AccountRepository accountRepository, ClientService<Client> clientService) {
        this.accountRepository = accountRepository;
        this.clientService = clientService;
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getById(long accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new IllegalArgumentException("Incorrect account id " + accountId)
        );
    }

    @Override
    public Account save(Account account) {
        Client client = clientService.getById(account.getRegisteredClient().getId());
        account.setRegisteredClient(client);

        return accountRepository.save(account);
    }

    @Override
    public Account updateStatus(long accountId, Status status) {
        Account account = getById(accountId);
        account.setStatus(status);

        return accountRepository.save(account);
    }

    @Override
    public Account addAmount(long accountId, BigDecimal amount) {
        Account account = getById(accountId);

        BigDecimal balanceBeforeAdd = account.getBalance();
        account.setBalance(balanceBeforeAdd.add(amount));

        return accountRepository.save(account);
    }

    @Override
    public Account subtractAmount(long accountId, BigDecimal amount) {
        Account account = getById(accountId);

        BigDecimal balanceBeforeSubtraction = account.getBalance();
        account.setBalance(balanceBeforeSubtraction.subtract(amount));

        if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            return null;
        }

        return accountRepository.save(account);
    }

    @Override
    public void drop(Account account) {
        if(account.getBalance().compareTo(BigDecimal.ONE) > 0){
            logger.info("I drop your money.");
        }
        accountRepository.delete(account);
    }
}
