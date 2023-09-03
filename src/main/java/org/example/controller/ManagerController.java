package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.ManagerDto;
import org.example.entities.Manager;
import org.example.enums.Status;
import org.example.service.ManagerService;
import org.example.service.dtoConvertor.ManagerDtoConvertor;
import org.springframework.http.ResponseEntity;
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
    public ManagerDto getById(@PathVariable(name = "id") long id) {
        Manager manager = managerService.getById(id);
        if (manager.getStatus().equals(Status.INACTIVE)) {
            return null;
        }
        return managerDtoConvertor.toDto(managerService.getById(id));
    }

    @PostMapping
    public Manager save(@RequestBody Manager manager) {
        return managerService.save(manager);
    }

    @PutMapping("/update/status/{id}")
    public ResponseEntity<Manager> updateStatus(
            @PathVariable long id,
            @RequestBody Manager manager
    ) {
        try {
            Manager managerWithUpdateStatus = managerService.updateStatus(id, manager.getStatus());
            return ResponseEntity.ok(managerWithUpdateStatus);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
