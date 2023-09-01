package org.example.service;

import org.example.entities.Account;
import org.example.enums.Status;
import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<Account> getAll();

    Account getById(long accountId);

    Account save(Account account);

    Account updateStatus(long accountId, Status status);

    Account addAmount(long accountId, BigDecimal amount);

    Account subtractAmount(long accountId, BigDecimal amount);

    void drop(Account account);
}
