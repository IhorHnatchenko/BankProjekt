package org.example.service;

import org.example.entities.Manager;
import org.example.repository.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ManagerServiceImplTest {

    @InjectMocks
    private ManagerServiceImpl managerServiceImpl;

    @Mock
    private ManagerRepository managerRepository;


    private final List<Manager> managerList = Arrays.asList(
            new Manager("John", "Doe"),
            new Manager("Alex", "Honkaj")
    );

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll() {

        when(managerRepository.findAll()).thenReturn(managerList);

        List<Manager> result = managerServiceImpl.getAll();

        //verify(managerRepository, times(1)).findAll();

        assertEquals(managerList, result);
    }

    @Test
    void getById() {

    }

    @Test
    void save() {
    }

    @Test
    void updateStatus() {
    }
}