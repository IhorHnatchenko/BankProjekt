package org.example.service;


import org.example.entities.Manager;
import org.example.enums.Status;
import java.util.List;

public interface ManagerService<M> {

    List<M> getAll();

    M getById(long managerId);

    M save(M manager);

    M updateStatus(long managerId, Status status);

    //Manager changeClient(long managerId, long clientId);
}
