package org.example.service;


import org.example.entities.Client;
import org.example.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client getById(long clientId) {
        return clientRepository.getReferenceById(clientId);
    }

    @Override
    public Client add(Client client) {
        return clientRepository.save(client);
    }
}
