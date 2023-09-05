package org.example.service;

import org.example.entities.Client;
import org.example.entities.Manager;
import org.example.enums.Status;
import java.util.List;

public interface ClientService {

    List<Client> getAll();

    Client getById(long clientId);

    Client save(Client client);

    Client updatePhoneNumber(long clientId, int phoneNumber);

    Client updateStatus(long clientId, Status status);

    Client changeManager(long clientId, Manager manager);

    Client changeEmail(long clientId, String email);

    Client changeAddress(long clientId, String address);

    Client getByEmail(String email);

}
