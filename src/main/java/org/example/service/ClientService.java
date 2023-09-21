package org.example.service;

import org.example.entities.Manager;
import org.example.enums.Status;

import java.util.List;

public interface ClientService<C> {

    List<C> getAll();

    C getById(long clientId);

    C save(C client);

    C updatePhoneNumber(long clientId, int phoneNumber);

    C updateStatus(long clientId, Status status);

    C changeManager(long clientId, Manager manager);

    C changeEmail(long clientId, String email);

    C changeAddress(long clientId, String address);

    C getByEmail(String email);

}
