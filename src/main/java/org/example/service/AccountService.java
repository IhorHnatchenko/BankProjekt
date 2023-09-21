package org.example.service;

import org.example.enums.Status;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService<AC> {

    List<AC> getAll();

    AC getById(long accountId);

    AC save(AC account);

    AC updateStatus(long accountId, Status status);

    AC addAmount(long accountId, BigDecimal amount);

    AC subtractAmount(long accountId, BigDecimal amount);

    void drop(AC account);
}
