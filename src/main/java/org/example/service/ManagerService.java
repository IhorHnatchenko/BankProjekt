package org.example.service;


import org.example.entities.Manager;
import org.example.enums.Status;
import java.util.List;

public interface ManagerService {

    List<Manager> getAll();

    Manager getById(long managerId);

    Manager save(Manager manager);

    Manager updateStatus(long managerId, Status status);

    //Manager changeClient(long managerId, long clientId);
}
