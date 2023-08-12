package org.example.service;



import org.example.entities.Client;


import java.util.List;

public interface ClientService {

    List<Client> getAll();

    Client getById(long clientId);

    Client add(Client client);
}
