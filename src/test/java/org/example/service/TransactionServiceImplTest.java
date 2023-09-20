package org.example.service;

import org.example.entities.Account;
import org.example.entities.Client;
import org.example.entities.Manager;
import org.example.entities.Transaction;
import org.example.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.enums.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private AccountServiceImpl accountService;

    @Test
    void getAll() {
        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(123, BigDecimal.valueOf(100), "testOne"));
        transactions.add(new Transaction(312, BigDecimal.valueOf(200), "testTwo"));

        when(transactionRepository.findAll()).thenReturn(transactions);

        assertEquals(transactions, transactionService.getAll());
    }

    @Test
    void getByIdWhenIdExists() {

        Transaction transaction = Transaction.builder()
                .iban(123)
                .amount(BigDecimal.valueOf(100))
                .description("testOne").build();

        when(transactionRepository.findById(transaction.getIban())).thenReturn(Optional.of(transaction));

        assertEquals(transaction, transactionService.getById(123));
    }

    @Test
    void getByIdWhenIdNotExists() {

        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(100))
                .description("testOne").build();

        when(transactionRepository.findById(transaction.getIban())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transactionService.getById(transaction.getIban()));
    }

    @Test
    void create() {

        Manager manager = Manager.builder()
                .id(1L)
                .status(ACTIVE)
                .firstName("Ihor")
                .lastName("Hnatchenko").build();

        Client client = Client.builder()
                .firstName("Alex")
                .lastName("Honkaj")
                .manager(manager).build();

        Account debitAccount = Account.builder()
                .name("Ihor")
                .balance(BigDecimal.valueOf(100))
                .status(ACTIVE)
                .registeredClient(client).build();

        Account creditAccount = Account.builder()
                .name("Ihor")
                .balance(BigDecimal.valueOf(100))
                .status(ACTIVE)
                .registeredClient(client).build();

        Transaction transaction = Transaction.builder()
                .iban(123)
                .amount(BigDecimal.valueOf(100))
                .description("testOne")
                .debitAccount(debitAccount)
                .creditAccount(creditAccount).build();

        when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction);

        assertEquals(transaction, transactionService.save(transaction));
    }

    @Test
    void transferWhenDebitAccountBalanceMoreThenZero() throws IllegalAccessException {

        Manager manager = Manager.builder()
                .id(1L)
                .status(ACTIVE)
                .firstName("Ihor")
                .lastName("Hnatchenko").build();

        Client client = Client.builder()
                .id(1L)
                .firstName("Alex")
                .lastName("Honkaj")
                .manager(manager).build();

        Account debitAccount = Account.builder()
                .id(1L)
                .name("Ihor")
                .balance(BigDecimal.valueOf(100))
                .status(ACTIVE)
                .registeredClient(client).build();

        Account creditAccount = Account.builder()
                .id(2L)
                .name("Ihor")
                .balance(BigDecimal.valueOf(100))
                .status(ACTIVE)
                .registeredClient(client).build();

        when(accountService.getById(debitAccount.getId())).thenReturn(debitAccount);
        when(accountService.getById(creditAccount.getId())).thenReturn(creditAccount);

        transactionService.transfer(
                debitAccount.getId(),
                creditAccount.getId(),
                BigDecimal.valueOf(100),
                "test transfer");

        Transaction transaction = Transaction.builder()
                .iban(123)
                .amount(BigDecimal.valueOf(100))
                .description("testOne")
                .debitAccount(debitAccount)
                .creditAccount(creditAccount).build();

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction saveTransaction = transactionService.save(transaction);

        assertEquals(BigDecimal.valueOf(0), saveTransaction.getDebitAccount().getBalance());
        assertEquals(BigDecimal.valueOf(200), saveTransaction.getCreditAccount().getBalance());
    }
}