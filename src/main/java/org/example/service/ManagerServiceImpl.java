package org.example.service;

import org.example.entities.Manager;
import org.example.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    @Override
    public Manager getById(long managerId) {
        return managerRepository.getReferenceById(managerId);
    }

    @Override
    public Manager add(Manager manager) {
        return managerRepository.save(manager);
    }
}
