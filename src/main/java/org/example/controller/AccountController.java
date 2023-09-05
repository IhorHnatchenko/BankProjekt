package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AccountDto;
import org.example.entities.Account;
import org.example.entities.Client;
import org.example.enums.Status;
import org.example.service.AccountService;
import org.example.service.dtoConvertor.AccountDtoConvertor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;

    private final AccountDtoConvertor accountDtoConvertor;

    public AccountController(AccountService accountService, AccountDtoConvertor accountDtoConvertor) {
        this.accountService = accountService;
        this.accountDtoConvertor = accountDtoConvertor;
    }

    @GetMapping
    public List<AccountDto> getAll() {
        return accountService.getAll()
                .stream()
                .map(accountDtoConvertor::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountDto getById(@PathVariable(name = "id") long id) {
        Account account = accountService.getById(id);
        Client registeredClient = account.getRegisteredClient();
        if (registeredClient.getStatus().equals(Status.INACTIVE) || account.getStatus().equals(Status.INACTIVE)){
            return null;
        }

        return accountDtoConvertor.toDto(accountService.getById(id));
    }

    @PostMapping
    public Account save(@RequestBody Account account) {
        return accountService.save(account);
    }

    @PutMapping("/update/status/{id}")
    public ResponseEntity<Account> updateStatus(
            @PathVariable long id,
            @RequestBody Account account) {

        try {
            Account accountWithUpdateStatus = accountService.updateStatus(id, account.getStatus());
            return ResponseEntity.ok(accountWithUpdateStatus);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/add/amount/{id}/{amount}")
    public ResponseEntity<Account> addAmount (
            @PathVariable long id,
            @PathVariable BigDecimal amount
    ){
        try {
            Account accountWithAddAmount = accountService.addAmount(id, amount);
            return ResponseEntity.ok(accountWithAddAmount);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/subtract/amount/{id}/{amount}")
    public ResponseEntity<Account> subtractAmount (
            @PathVariable long id,
            @PathVariable BigDecimal amount
            ){
        try {
            Account accountWithSubtractAmount = accountService.subtractAmount(id, amount);
            return ResponseEntity.ok(accountWithSubtractAmount);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/drop")
    public void dropAccount (
            @RequestBody Account account
    ) {
        accountService.drop(account);
    }


}
