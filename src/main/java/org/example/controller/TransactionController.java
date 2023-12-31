package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.TransactionDto;
import org.example.entities.Transaction;
import org.example.service.TransactionService;
import org.example.service.dtoConvertor.TransactionDtoConvertor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("transfers")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService<Transaction> transferService;

    private final TransactionDtoConvertor transactionDtoConvertor;

    @GetMapping
    public List<TransactionDto> getAll() {
        return transferService.getAll()
                .stream()
                .map(transactionDtoConvertor::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TransactionDto getById(@PathVariable(name = "id") long id) {
        return transactionDtoConvertor.toDto(transferService.getById(id));
    }

    @PostMapping
    public Transaction save(@RequestBody Transaction transaction) {
        return transferService.save(transaction);
    }

    @PostMapping("/{accountOneId}/{accountTwoId}/{amount}")
    public void transfer(
            @PathVariable("accountOneId") long accountOneId,
            @PathVariable("accountTwoId") long accountTwoId,
            @PathVariable("amount") BigDecimal balance,
            @RequestBody String description) throws IllegalAccessException {

        transferService.transfer(accountOneId, accountTwoId, balance, description);
    }
}
