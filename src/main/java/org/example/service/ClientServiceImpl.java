package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entities.Client;
import org.example.entities.Manager;
import org.example.enums.Status;
import org.example.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService<Client> {

    private final ClientRepository clientRepository;

    private final ManagerService<Manager> managerService;

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client getById(long clientId) {
        return clientRepository.findById(clientId).orElseThrow(
                () -> new IllegalArgumentException("Incorrect client id " + clientId)
        );
    }

    @Override
    public Client save(Client client) {
        Manager manager = managerService.getById(client.getManager().getId());
        client.setManager(manager);

        return clientRepository.save(client);
    }

    @Override
    public Client updatePhoneNumber(long clientId, int phoneNumber) {
        Client client = getById(clientId);
        client.setPhone(phoneNumber);

        return clientRepository.save(client);
    }

    @Override
    public Client updateStatus(long clientId, Status status) {
        Client client = getById(clientId);
        client.setStatus(status);

        return clientRepository.save(client);
    }

    @Override
    public Client changeManager(long clientId, Manager manager) {
        Client client = getById(clientId);
        client.setManager(manager);

        return clientRepository.save(client);
    }

    @Override
    public Client changeEmail(long clientId, String email) {
        Client client = getById(clientId);
        client.setEmail(email);

        return clientRepository.save(client);
    }

    @Override
    public Client changeAddress(long clientId, String address) {
        Client client = getById(clientId);
        client.setAddress(address);

        return clientRepository.save(client);
    }

    @Override
    public Client getByEmail(String email) {
        if (email == null || "".equals(email)) {
            throw new IllegalArgumentException("Incorrect client email " + email);
        }
        return clientRepository.findByEmail(email);
    }
}
