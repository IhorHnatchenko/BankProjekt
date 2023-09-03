package org.example.service.dtoConvertor;

import org.example.dto.TransactionDto;
import org.example.entities.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoConvertor<ENTITY, DTO> implements Converter<Transaction, TransactionDto> {
    @Override
    public TransactionDto toDto(Transaction transaction) {
        return new TransactionDto(transaction.getAmount(), transaction.getDescription());
    }

    @Override
    public Transaction toEntity(TransactionDto transactionDto) {
        return new Transaction(transactionDto.getBalance(), transactionDto.getDescription());
    }
}