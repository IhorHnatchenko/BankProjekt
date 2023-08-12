package org.example.controller;

import org.example.entities.Client;
import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    List<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    Client getById(@PathVariable(name = "id") long id){
        return clientService.getById(id);
    }

    @PostMapping
    ResponseEntity<Client> add(@RequestBody Client client){
        return ResponseEntity.ok(clientService.add(client));
    }
}
