package org.example.service;

import org.example.entities.Account;
import org.example.enums.Status;
import org.example.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAll() {
        //logger.info("Call method get all.");

        return accountRepository.findAll();
    }

    @Override
    public Account getById(long accountId) {
        return accountRepository.getReferenceById(accountId);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account updateStatus(long accountId, Status status) {
        Account account = accountRepository.getReferenceById(accountId);

        account.setStatus(status);
        return accountRepository.save(account);
    }

    @Override
    public Account addAmount(long accountId, BigDecimal amount) {
        Account account = accountRepository.getReferenceById(accountId);

        BigDecimal balanceBeforeAdd = account.getBalance();
        account.setBalance(balanceBeforeAdd.add(amount));

        return accountRepository.save(account);
    }

    @Override
    public Account subtractAmount(long accountId, BigDecimal amount) {
        Account account = accountRepository.getReferenceById(accountId);

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