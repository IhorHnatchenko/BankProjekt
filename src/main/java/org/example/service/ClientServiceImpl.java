package org.example.service;

import org.example.entities.Client;
import org.example.entities.Manager;
import org.example.enums.Status;
import org.example.repository.ClientRepository;
import org.example.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ManagerRepository managerRepository;

    public ClientServiceImpl(ClientRepository clientRepository, ManagerRepository managerRepository) {
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
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
        if (client.getManager().getId() != 0){
            Manager manager = managerRepository.findById(client.getManager().getId()).get();
            client.setManager(manager);
        }
        return clientRepository.save(client);
    }

    @Override
    public Client updatePhoneNumber(long clientId, int phoneNumber) {
        Client client = clientRepository.getReferenceById(clientId);

        client.setPhone(phoneNumber);
        return clientRepository.save(client);

    }

    @Override
    public Client updateStatus(long clientId, Status status) {
        Client client = clientRepository.getReferenceById(clientId);

        client.setStatus(status);
        return clientRepository.save(client);
    }

    @Override
    public Client changeManager(long clientId, Manager manager) {
        Client client = clientRepository.getReferenceById(clientId);
        client.setManager(manager);

        return clientRepository.save(client);
    }

    @Override
    public Client changeEmail(long clientId, String email) {
        Client client = clientRepository.getReferenceById(clientId);

        client.setEmail(email);
        return clientRepository.save(client);
    }

    @Override
    public Client changeAddress(long clientId, String address) {
        Client client = clientRepository.getReferenceById(clientId);

        client.setAddress(address);
        return clientRepository.save(client);
    }

    @Override
    public Client getByEmail(String email) {
        return clientRepository.findByEmail(email);
    }




}
