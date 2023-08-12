package org.example.controller;

import org.example.entities.Client;
import org.example.entities.Manager;
import org.example.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/getAll")
    List<Manager> getAll() {
        return managerService.getAll();
    }

    @GetMapping("/{id}")
    Manager getById(@PathVariable(name = "id") long id){
        return managerService.getById(id);
    }

    @PostMapping
    ResponseEntity<Manager> add(@RequestBody Manager manager){
        return ResponseEntity.ok(managerService.add(manager));
    }

}
