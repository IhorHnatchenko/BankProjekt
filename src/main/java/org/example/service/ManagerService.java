package org.example.service;

import org.example.entities.Client;
import org.example.entities.Manager;

import java.util.List;

public interface ManagerService {

    List<Manager> getAll();

    Manager getById(long managerId);

    Manager add(Manager manager);
}
