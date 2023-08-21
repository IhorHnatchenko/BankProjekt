package org.example.service;

import org.example.entities.Account;

import java.util.List;

public interface AccountService {

    List<Account> getAll();

    Account getById(long accountId);

    Account save(Account account);
}
