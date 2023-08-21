package org.example.controller;

import org.example.dto.ManagerDto;
import org.example.service.ManagerService;
import org.example.service.dtoConvertor.ManagerDtoConvertor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("managers")
public class ManagerController {

    private final ManagerService managerService;

    private final ManagerDtoConvertor managerDtoConvertor;

    public ManagerController(ManagerService managerService, ManagerDtoConvertor managerDtoConvertor) {
        this.managerService = managerService;
        this.managerDtoConvertor = managerDtoConvertor;
    }

    @GetMapping
    public List<ManagerDto> getAll() {
        return managerService.getAll()
                .stream()
                .map(managerDtoConvertor::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ManagerDto getById(@PathVariable(name = "id") long id){
        return managerDtoConvertor.toDto(managerService.getById(id));
    }

    @PostMapping
    public ManagerDto save(@RequestBody ManagerDto managerDto){
        return managerDtoConvertor.toDto(managerService.save(managerDtoConvertor.toEntity(managerDto)));
    }

}
