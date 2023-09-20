package org.example.service;

import org.example.entities.Account;
import org.example.entities.Client;
import org.example.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.example.enums.Status.ACTIVE;
import static org.example.enums.Status.INACTIVE;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private ClientServiceImpl clientService;


    @Test
    void getAll() {
        List<Account> accounts = new ArrayList<>();

        accounts.add(new Account(1L, "Ihor", BigDecimal.valueOf(100), ACTIVE));
        accounts.add(new Account(2L, "Anna", BigDecimal.valueOf(500), ACTIVE));
        accounts.add(new Account(3L, "Yi", BigDecimal.valueOf(300), ACTIVE));

        when(accountRepository.findAll()).thenReturn(accounts);

        assertEquals(accounts, accountService.getAll());
    }

    @Test
    void getByIdWhenIdExists() {

        Account account = Account.builder()
                .id(1L)
                .name("Ihor")
                .balance(BigDecimal.valueOf(100))
                .status(ACTIVE).build();

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        assertEquals(account, accountService.getById(1L));
    }

    @Test
    void getByIdWhenIdNotExists() {
        Account account = Account.builder()
                .name("Ihor")
                .balance(BigDecimal.valueOf(100))
                .status(ACTIVE).build();

        when(accountRepository.findById(account.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> accountService.getById(account.getId()));
    }

    @Test
    void create() {

        Client client = Client.builder()
                .id(1)
                .status(ACTIVE)
                .firstName("Alex")
                .lastName("Honkaj").build();

        when(clientService.getById(client.getId())).thenReturn(client);

        Account account = Account.builder()
                .name("Ihor")
                .balance(BigDecimal.valueOf(100))
                .status(ACTIVE)
                .registeredClient(client).build();

        when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        assertEquals(account, accountService.save(account));
    }

    @Test
    void updateStatus() {

        Account account = Account.builder()
                .id(1L)
                .name("Ihor")
                .balance(BigDecimal.valueOf(100))
                .status(ACTIVE).build();

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        Account withUpdateStatus = accountService.updateStatus(account.getId(), INACTIVE);

        assertEquals(INACTIVE, withUpdateStatus.getStatus());
    }

    @Test
    void addAmount() {
        Account account = Account.builder()
                .id(1L)
                .name("Ihor")
                .balance(BigDecimal.valueOf(100))
                .status(ACTIVE).build();

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        Account withAddAmount = accountService.addAmount(account.getId(), BigDecimal.valueOf(100));

        assertEquals(BigDecimal.valueOf(200), withAddAmount.getBalance());
    }

    @Test
    void subtractAmount() {

        Account account = Account.builder()
                .id(1L)
                .name("Ihor")
                .balance(BigDecimal.valueOf(500))
                .status(ACTIVE).build();

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        Account withSubtractAmount = accountService.subtractAmount(account.getId(), BigDecimal.valueOf(300));

        assertEquals(BigDecimal.valueOf(200), withSubtractAmount.getBalance());
    }

    @Test
    void drop() {

    }
}