package org.example.controller;

import org.example.dto.AccountDto;
import org.example.service.AccountService;
import org.example.service.dtoConvertor.AccountDtoConvertor;
import org.springframework.web.bind.annotation.*;

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
        return accountDtoConvertor.toDto(accountService.getById(id));
    }

    @PostMapping
    public AccountDto save(@RequestBody AccountDto accountDto){
        return accountDtoConvertor.toDto(accountService.save(accountDtoConvertor.toEntity(accountDto)));
    }


}
