package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.AccountDto;
import org.example.entities.Account;
import org.example.entities.Client;
import org.example.enums.Status;
import org.example.exceptions.InvalidStatusException;
import org.example.service.AccountService;
import org.example.service.dtoConvertor.AccountDtoConvertor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService<Account> accountService;

    private final AccountDtoConvertor accountDtoConvertor;

    @GetMapping
    public List<AccountDto> getAll() {
        return accountService.getAll()
                .stream()
                .map(accountDtoConvertor::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountDto getById(@PathVariable(name = "id") long id) throws InvalidStatusException {
        Account account = accountService.getById(id);
        Client registeredClient = account.getRegisteredClient();
        if (Status.INACTIVE == registeredClient.getStatus()) {
            throw new InvalidStatusException(String.format("Client with id %d is inactive: ", registeredClient.getId()));
        } else if (Status.INACTIVE == account.getStatus()) {
            throw new InvalidStatusException(String.format("Account with id %d is inactive: ", account.getId()));
        }

        return accountDtoConvertor.toDto(accountService.getById(id));
    }

    @PostMapping
    public Account save(@RequestBody Account account) {
        return accountService.save(account);
    }

    @PutMapping("/update/status/{id}")
    public AccountDto updateStatus(
            @PathVariable long id,
            @RequestBody AccountDto accountDto) {

        return accountDtoConvertor.toDto(accountService.updateStatus(id, accountDto.getStatus()));
    }

    @PutMapping("/add/amount/{id}/{amount}")
    public AccountDto addAmount(
            @PathVariable long id,
            @PathVariable BigDecimal amount) {

        return accountDtoConvertor.toDto(accountService.addAmount(id, amount));
    }

    @PutMapping("/subtract/amount/{id}/{amount}")
    public AccountDto subtractAmount(
            @PathVariable long id,
            @PathVariable BigDecimal amount
    ) {

        return accountDtoConvertor.toDto(accountService.subtractAmount(id, amount));
    }

    @DeleteMapping("/drop")
    public void dropAccount(
            @RequestBody Account account
    ) {
        accountService.drop(account);
    }
}
