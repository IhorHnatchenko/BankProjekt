package org.example.service;


import jakarta.persistence.EntityNotFoundException;
import org.example.entities.Client;
import org.example.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client getById(long clientId) {
        return clientRepository.getReferenceById(clientId);
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client updatePhoneNumber(long clientId, int phoneNumber) {
        Client client = clientRepository.getReferenceById(clientId);

        client.setPhone(phoneNumber);
        return clientRepository.save(client);

    }

    @Override
    public Client updateStatus(long clientId, int status) {
        Client client = clientRepository.getReferenceById(clientId);

        client.setStatus(status);
        return clientRepository.save(client);
    }


}
