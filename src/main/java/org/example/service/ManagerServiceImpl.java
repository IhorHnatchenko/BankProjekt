package org.example.service;

import org.example.entities.Manager;
import org.example.enums.Status;
import org.example.repository.ClientRepository;
import org.example.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    private final ClientRepository clientRepository;

    public ManagerServiceImpl(ManagerRepository managerRepository, ClientRepository clientRepository) {
        this.managerRepository = managerRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    @Override
    public Manager getById(long managerId) {
        return managerRepository.getReferenceById(managerId);
    }

    @Override
    public Manager save(Manager manager) {
        return managerRepository.save(manager);
    }

    @Override
    public Manager updateStatus(long managerId, Status status) {
        Manager manager = managerRepository.getReferenceById(managerId);

        manager.setStatus(status);
        return managerRepository.save(manager);
    }

/*    @Override
    public Manager changeClient(long managerId,long clientId) {
        Manager manager = managerRepository.getReferenceById(managerId);

        Client client = clientRepository.getReferenceById(clientId);

        client.setManager(manager);
        return managerRepository.save(manager);
    }*/
}
