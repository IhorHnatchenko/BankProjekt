package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ManagerDto;
import org.example.entities.Manager;
import org.example.enums.Status;
import org.example.exceptions.InvalidStatusException;
import org.example.service.ManagerService;
import org.example.service.dtoConvertor.ManagerDtoConvertor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService<Manager> managerService;

    private final ManagerDtoConvertor managerDtoConvertor;

    @GetMapping
    public List<ManagerDto> getAll() {
        return managerService.getAll()
                .stream()
                .map(managerDtoConvertor::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ManagerDto getById(@PathVariable(name = "id") long id) throws InvalidStatusException {
        Manager manager = managerService.getById(id);

        if (Status.INACTIVE == manager.getStatus()) {
            throw new InvalidStatusException(String.format("Manager with id %d is inactive: ", manager.getId()));
        }
        return managerDtoConvertor.toDto(managerService.getById(id));
    }

    @PostMapping
    public Manager save(@RequestBody Manager manager) {
        return managerService.save(manager);
    }

    @PutMapping("/update/status/{id}")
    public ManagerDto updateStatus(
            @PathVariable long id,
            @RequestBody ManagerDto managerDot
    ) {
        return managerDtoConvertor.toDto(managerService.updateStatus(id, managerDot.getStatus()));
    }
}
