package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AgreementDto;
import org.example.entities.Agreement;
import org.example.entities.Product;
import org.example.enums.Status;
import org.example.service.AgreementService;
import org.example.service.dtoConvertor.AgreementDtoConvertor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("agreements")
public class AgreementController {

    private final AgreementService agreementService;

    private final AgreementDtoConvertor agreementDtoConvertor;

    public AgreementController(AgreementService agreementService, AgreementDtoConvertor agreementDtoConvertor) {
        this.agreementService = agreementService;
        this.agreementDtoConvertor = agreementDtoConvertor;
    }

    @GetMapping
    public List<AgreementDto> getAll() {
        return agreementService.getAll()
                .stream()
                .map(agreementDtoConvertor::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AgreementDto getById(@PathVariable(name = "id") long id) {
        Agreement agreement = agreementService.getById(id);
        Product product = agreement.getProduct();
        if (product.getStatus().equals(Status.INACTIVE) || agreement.getStatus().equals(Status.INACTIVE)) {
            return null;
        }

        return agreementDtoConvertor.toDto(agreementService.getById(id));
    }

    @PostMapping
    public AgreementDto save(@RequestBody AgreementDto agreementDto) {
        return agreementDtoConvertor.toDto(agreementService.save(agreementDtoConvertor.toEntity(agreementDto)));
    }

    @PutMapping("update/status/{id}")
    public ResponseEntity<Agreement> updateStatus(
            @PathVariable long id,
            @RequestBody Agreement agreement
    ) {
        try {
            Agreement agreementWithUpdateStatus = agreementService.updateStatus(id, agreement.getStatus());
            return ResponseEntity.ok(agreementWithUpdateStatus);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
