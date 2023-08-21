package org.example.controller;

import org.example.dto.AgreementDto;
import org.example.service.AgreementService;
import org.example.service.dtoConvertor.AgreementDtoConvertor;
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
    public List<AgreementDto> getAll(){
        return agreementService.getAll()
                .stream()
                .map(agreementDtoConvertor::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AgreementDto getById(@PathVariable(name = "id") long id){
        return agreementDtoConvertor.toDto(agreementService.getById(id));
    }

    @PostMapping
    public AgreementDto save(@RequestBody AgreementDto agreementDto){
        return agreementDtoConvertor.toDto(agreementService.save(agreementDtoConvertor.toEntity(agreementDto)));
    }
}
