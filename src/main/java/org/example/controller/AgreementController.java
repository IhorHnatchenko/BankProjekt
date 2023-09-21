package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.AgreementDto;
import org.example.entities.Agreement;
import org.example.entities.Product;
import org.example.enums.Status;
import org.example.exceptions.InvalidStatusException;
import org.example.service.AgreementService;
import org.example.service.dtoConvertor.AgreementDtoConvertor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("agreements")
@RequiredArgsConstructor
public class AgreementController {

    private final AgreementService<Agreement> agreementService;

    private final AgreementDtoConvertor agreementDtoConvertor;

    @GetMapping
    public List<AgreementDto> getAll() {
        return agreementService.getAll()
                .stream()
                .map(agreementDtoConvertor::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AgreementDto getById(@PathVariable(name = "id") long id) throws InvalidStatusException {
        Agreement agreement = agreementService.getById(id);
        Product product = agreement.getProduct();
        if (Status.INACTIVE == product.getStatus()) {
            throw new InvalidStatusException(String.format("Product with id %d is inactive: ", product.getId()));
        } else if (Status.INACTIVE == agreement.getStatus()) {
            throw new InvalidStatusException(String.format("Agreement with id %d is inactive: ", agreement.getId()));
        }

        return agreementDtoConvertor.toDto(agreementService.getById(id));
    }

    @PostMapping
    public Agreement save(@RequestBody Agreement agreement) {
        return agreementService.save(agreement);
    }

    @PutMapping("update/status/{id}")
    public AgreementDto updateStatus(
            @PathVariable long id,
            @RequestBody AgreementDto agreementDto
    ) {
        return agreementDtoConvertor.toDto(agreementService.updateStatus(id, agreementDto.getStatus()));
    }
}