package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ClientDto;
import org.example.dto.ManagerDto;
import org.example.entities.Client;
import org.example.enums.Status;
import org.example.exceptions.InvalidStatusException;
import org.example.service.ClientService;
import org.example.service.dtoConvertor.ClientDtoConvertor;
import org.example.service.dtoConvertor.ManagerDtoConvertor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientDtoConvertor clientDtoConverter;

    private final PasswordEncoder passwordEncoder;

    private final ClientService<Client> clientService;

    private final ManagerDtoConvertor managerDtoConvertor;

    @GetMapping
    public List<ClientDto> getAll() {
        return clientService.getAll()
                .stream()
                .map(clientDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDto getById(@PathVariable(name = "id") long id) throws InvalidStatusException {
        Client client = clientService.getById(id);
        if (Status.INACTIVE == client.getStatus()) {
            throw new InvalidStatusException(String.format("Client with id %d is inactive: ", client.getId()));
        }
        return clientDtoConverter.toDto(clientService.getById(id));
    }

    @GetMapping("/email/{email}")
    public ClientDto getByEmail(@PathVariable(name = "email") String email) throws InvalidStatusException {
        Client client = clientService.getByEmail(email);

        if (Status.INACTIVE == client.getStatus()) {
            throw new InvalidStatusException(String.format("Client with id %d is inactive: ", client.getId()));
        }

        return clientDtoConverter.toDto(client);
    }

    @PostMapping
    public Client save(@RequestBody Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientService.save(client);
    }

    @PutMapping("/update/status/{id}")
    public ClientDto updateStatus(
            @PathVariable long id,
            @RequestBody ClientDto clientDto
    ) {

        return clientDtoConverter
                .toDto(clientService
                        .updateStatus(id, clientDto.getStatus()));
    }

    @PutMapping("/change/manager/{id}")
    public ClientDto changeManager(
            @PathVariable long id,
            @RequestBody ManagerDto managerDto
    ) throws InvalidStatusException {

        Client client = clientService.getById(id);

        if (Status.INACTIVE == client.getStatus()) {
            throw new InvalidStatusException(String.format("Client with id %d is inactive: ",
                    client.getId()));
        } else if (Status.INACTIVE == managerDto.getStatus()) {
            throw new InvalidStatusException(String.format("Manager with id %d is inactive: ",
                    managerDto.getId()));
        }

        return clientDtoConverter
                .toDto(clientService
                        .changeManager(id, managerDtoConvertor
                                .toEntity(managerDto)));
    }

    @PutMapping("/update/phone/{id}")
    public Client updatePhone(
            @PathVariable long id,
            @RequestBody Client client
    ) throws InvalidStatusException {

        if (Status.INACTIVE == client.getStatus()) {
            throw new InvalidStatusException(String.format("Client with id %d is inactive: ",
                    client.getId()));
        }

        return clientService.updatePhoneNumber(id, client.getPhone());
    }

    @PutMapping("/change/email/{id}")
    public ClientDto updateEmail(
            @PathVariable long id,
            @RequestBody ClientDto clientDto
    ) throws InvalidStatusException {

        if (Status.INACTIVE == clientDto.getStatus()) {
            throw new InvalidStatusException(String.format("Client with id %d is inactive: ",
                    clientDto.getId()));
        }

        return clientDtoConverter
                .toDto(clientService
                        .changeEmail(id, clientDtoConverter
                                .toEntity(clientDto)
                                .getEmail()));
    }

    @PutMapping("/change/address/{id}")
    public Client updateAddress(
            @PathVariable long id,
            @RequestBody Client client
    ) throws InvalidStatusException {

        if (Status.INACTIVE == client.getStatus()) {
            throw new InvalidStatusException(String.format("Client with id %d is inactive: ",
                    client.getId()));
        }

        return clientService.changeAddress(id, client.getAddress());
    }
}