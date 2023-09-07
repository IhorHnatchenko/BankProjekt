package org.example.service;

import org.example.entities.Manager;
import org.example.repository.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import static org.example.enums.Status.ACTIVE;
import static org.example.enums.Status.INACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManagerServiceImplTest {

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private ManagerServiceImpl managerService;


    @Test
    void GetAll_ReturnAll() {
        List<Manager> managers = new ArrayList<>();
        Timestamp create_at = Timestamp.valueOf("2023-08-30 20:52:00");

        managers.add(new Manager(ACTIVE, "John", "Doe", create_at, null));
        managers.add(new Manager(ACTIVE, "Alex", "Honkaj", create_at, null));
        managers.add(new Manager(ACTIVE, "Bob", "Marly", create_at, null));

        when(managerRepository.findAll()).thenReturn(managers);

        List<Manager> savedAll = managerService.getAll();

        assertEquals(managers, savedAll);
    }

    @Test
    void getById_ReturnById() {

        Manager manager = Manager.builder()
                .id(1)
                .status(ACTIVE)
                .firstName("Ihor")
                .lastName("Hnatchenko").build();

        when(managerRepository.getReferenceById(manager.getId())).thenReturn(manager);

        Manager saveById = managerService.getById(1);

        assertEquals(manager, saveById);
    }

    @Test
    void AddAndSave_ReturnSave() {

        Manager manager = Manager.builder()
                .status(ACTIVE)
                .firstName("Ihor")
                .lastName("Hnatchenko").build();

        when(managerRepository.save(Mockito.any(Manager.class))).thenReturn(manager);

        Manager saved = managerService.save(manager);

        assertEquals(manager, saved);
    }


    @Test
    void GetById_Save_updateStatus_ReturnWithUpdateStatus() {

        Manager manager = Manager.builder()
                .id(1)
                .status(ACTIVE)
                .firstName("Ihor")
                .lastName("Hnatchenko").build();

        when(managerRepository.getReferenceById(manager.getId())).thenReturn(manager);

        when(managerRepository.save(Mockito.any(Manager.class))).thenReturn(manager);

        Manager withUpdateStatus = managerService.updateStatus(manager.getId(), INACTIVE);

        assertEquals(INACTIVE, withUpdateStatus.getStatus());
    }
}