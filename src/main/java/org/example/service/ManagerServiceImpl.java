package org.example.service;

import org.example.entities.Manager;
import org.example.enums.Status;
import org.example.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    @Override
    public Manager getById(long managerId) {
        assert managerRepository != null;
        return managerRepository.getReferenceById(managerId);
    }

    @Override
    public Manager save(Manager manager) {
        assert managerRepository != null;
        return managerRepository.save(manager);
    }

    @Override
    public Manager updateStatus(long managerId, Status status) {
        assert managerRepository != null;
        Manager manager = managerRepository.getReferenceById(managerId);

        manager.setStatus(status);
        return managerRepository.save(manager);
    }
}
