package org.example.service.dtoConvertor;

import org.example.dto.AccountDto;
import org.example.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoConvertor<ENTITY, DTO> implements Converter<Account, AccountDto> {

    @Override
    public AccountDto toDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getName(),
                account.getBalance(),
                account.getStatus());
    }

    @Override
    public Account toEntity(AccountDto accountDto) {
        return new Account(
                accountDto.getId(),
                accountDto.getName(),
                accountDto.getBalance(),
                accountDto.getStatus());
    }
}
