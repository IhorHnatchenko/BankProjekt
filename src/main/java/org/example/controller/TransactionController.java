package org.example.controller;

import org.example.dto.TransactionDto;
import org.example.service.TransactionService;
import org.example.service.dtoConvertor.TransactionDtoConvertor;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("transfers")
public class TransactionController {
    private final TransactionService transferService;

    private final TransactionDtoConvertor transactionDtoConvertor;

    public TransactionController(TransactionService transferService, TransactionDtoConvertor transactionDtoConvertor) {
        this.transferService = transferService;
        this.transactionDtoConvertor = transactionDtoConvertor;
    }

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
    public TransactionDto save(@RequestBody TransactionDto transactionDto) {
        return transactionDtoConvertor.toDto(transferService.save(transactionDtoConvertor.toEntity(transactionDto)));
    }

    @PostMapping("/{accountOneId}/{accountTwoId}/{amount}")
    public void transfer(
            @PathVariable("accountOneId") long accountOneId,
            @PathVariable("accountTwoId") long accountTwoId,
            @PathVariable("amount") BigDecimal balance,
            @RequestBody String description) {
        try {
            transferService.transfer(accountOneId, accountTwoId, balance, description);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
