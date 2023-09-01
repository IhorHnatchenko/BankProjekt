package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.ClientDto;
import org.example.entities.Client;
import org.example.entities.Manager;
import org.example.enums.Status;
import org.example.service.ClientService;
import org.example.service.dtoConvertor.ClientDtoConvertor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("clients")
public class ClientController {

    private final ClientDtoConvertor clientDtoConverter;

    private final ClientService clientService;

    public ClientController(ClientService clientService, ClientDtoConvertor clientDtoConverter) {
        this.clientService = clientService;
        this.clientDtoConverter = clientDtoConverter;
    }

    @GetMapping
    public List<ClientDto> getAll() {
        return clientService.getAll()
                .stream()
                .map(clientDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDto getById(@PathVariable(name = "id") long id) {
        Client client = clientService.getById(id);
        if (client.getStatus().equals(Status.INACTIVE)) {
            return null;
        }
        return clientDtoConverter.toDto(clientService.getById(id));
    }

    @PostMapping
    public ClientDto save(@RequestBody ClientDto clientDto) {
        return clientDtoConverter.toDto(clientService.save(clientDtoConverter.toEntity(clientDto)));
    }

    @PutMapping("/update/status/{id}")
    public ResponseEntity<Client> updateStatus(
            @PathVariable long id,
            @RequestBody Client client
    ) {
        try {
            Client clientWithUpdateStatus = clientService.updateStatus(id, client.getStatus());
            return ResponseEntity.ok(clientWithUpdateStatus);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/change/manager/{id}")
    public ResponseEntity<Client> changeManager(
            @PathVariable long id,
            @RequestBody Manager manager) {
        if (clientService.getById(id).getStatus().equals(Status.INACTIVE)) {
            // Напиши лог.
            return null;
        }

        try {
            Client clientWithChangeManager = clientService.changeManager(id, manager);
            return ResponseEntity.ok(clientWithChangeManager);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/update/phone/{id}")
    public ResponseEntity<Client> updatePhone(
            @PathVariable long id,
            @RequestBody Client client
    ) {
        if (clientService.getById(id).getStatus().equals(Status.INACTIVE)) {
            // Напиши лог.
            return null;
        }

        try {
            Client updatedClient = clientService.updatePhoneNumber(id, client.getPhone());
            return ResponseEntity.ok(updatedClient);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/change/email/{id}")
    public ResponseEntity<Client> updateEmail(
            @PathVariable long id,
            @RequestBody Client client
    ) {
        if (clientService.getById(id).getStatus().equals(Status.INACTIVE)) {
            // Напиши лог.
            return null;
        }

        try {
            Client clientWithUpdateEmail = clientService.changeEmail(id, client.getEmail());
            return ResponseEntity.ok(clientWithUpdateEmail);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/change/address/{id}")
    public ResponseEntity<Client> updateAddress(
            @PathVariable long id,
            @RequestBody Client client
    ) {
        if (clientService.getById(id).getStatus().equals(Status.INACTIVE)) {
            // Напиши лог.
            return null;
        }

        try {
            Client clientWithUpdateAddress = clientService.changeAddress(id, client.getAddress());
            return ResponseEntity.ok(clientWithUpdateAddress);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


/*    @DeleteMapping("/drop/account/{id}")
    public ResponseEntity<Client> dropAccount(
            @PathVariable long id,
            @RequestBody Account account
    ) {
        try {
            Client clientWithDropAccount = clientService.dropAccount(id, account);
            return ResponseEntity.ok(clientWithDropAccount);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }*/

/*    {
        "status": 1,
            "name": "Ihor",
            "type": 1,
            "balance": 100,
            "currencyCode": "USD"
    }*/

    /*{
    "status": 1,
    "taxCode": 987654,
    "firstName": "Ihor",
    "lastName": "Hnatchenko",
    "email": "Ihor@gmail.com",
    "address": "hello",
    "phone": 123456
}*/


}
