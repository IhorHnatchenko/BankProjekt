package org.example.service;

import org.example.entities.Client;
import org.example.entities.Manager;
import org.example.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.example.enums.Status.ACTIVE;
import static org.example.enums.Status.INACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ManagerServiceImpl managerService;


    @Test
    void getAll() {
        List<Client> clients = new ArrayList<>();

        clients.add(new Client("Ihor", "Hnatchenko"));
        clients.add(new Client("Anna", "Hnatchenko"));
        clients.add(new Client("Yi", "Honkaj"));

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> savedAll = clientService.getAll();

        assertEquals(clients, savedAll);
    }

    @Test
    void getByIdWhenIdExists() {

        Client client = Client.builder()
                .id(1)
                .status(ACTIVE)
                .firstName("Alex")
                .lastName("Honkaj").build();

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        assertEquals(client, clientService.getById(1));
    }

    @Test
    void getByIdWhenIdNotExists() {
        Client client = Client.builder()
                .status(ACTIVE)
                .firstName("Alex")
                .lastName("Honkaj").build();

        when(clientRepository.findById(client.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> clientService.getById(client.getId()));
    }

    @Test
    void create() {

        Manager manager = Manager.builder()
                .id(1L)
                .status(ACTIVE)
                .firstName("Ihor")
                .lastName("Hnatchenko").build();

        when(managerService.getById(manager.getId())).thenReturn(manager);

        Client client = Client.builder()
                .firstName("Alex")
                .lastName("Honkaj")
                .manager(manager).build();

        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        Client saved = clientService.save(client);
        assertEquals(client, saved);
    }


    @Test
    void updateStatus() {

        Client client = Client.builder()
                .id(1L)
                .status(ACTIVE)
                .firstName("Alex")
                .lastName("Honkaj").build();

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        Client withUpdateStatus = clientService.updateStatus(client.getId(), INACTIVE);

        assertEquals(INACTIVE, withUpdateStatus.getStatus());
    }

    @Test
    void updatePhoneNumber() {

        Client client = Client.builder()
                .id(1L)
                .status(ACTIVE)
                .firstName("Alex")
                .lastName("Honkaj")
                .phone(987654321).build();

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        Client withUpdatePhoneNumber = clientService.updatePhoneNumber(client.getId(), 123456789);

        assertEquals(123456789, withUpdatePhoneNumber.getPhone());
    }


    @Test
    void changeManager() {

        Manager manager = Manager.builder()
                .id(1L)
                .status(ACTIVE)
                .firstName("Ihor")
                .lastName("Hnatchenko").build();

        Client client = Client.builder()
                .firstName("Alex")
                .lastName("Honkaj")
                .manager(manager).build();

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        Client withChangeManager = clientService.changeManager(client.getId(), manager);

        assertEquals(manager, withChangeManager.getManager());
    }

    @Test
    void changeEmail() {

        Client client = Client.builder()
                .id(1L)
                .status(ACTIVE)
                .firstName("Alex")
                .lastName("Honkaj")
                .email("Yi@gmail.com").build();

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        Client withChangeEmail = clientService.changeEmail(client.getId(), "YiHome@gmail.com");

        assertEquals("YiHome@gmail.com", withChangeEmail.getEmail());
    }

    @Test
    void changeAddress() {

        Client client = Client.builder()
                .id(1L)
                .status(ACTIVE)
                .firstName("Alex")
                .lastName("Honkaj")
                .address("hello").build();

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        Client withChangeAddress = clientService.changeAddress(client.getId(), "Hello");

        assertEquals("Hello", withChangeAddress.getAddress());
    }

    @Test
    void getByEmailWhenEmailExists() {

        Client client = Client.builder()
                .id(1L)
                .status(ACTIVE)
                .firstName("Alex")
                .lastName("Honkaj")
                .email("Yi@gmail.com").build();

        when(clientRepository.findByEmail(client.getEmail())).thenReturn(client);

        assertEquals(client, clientService.getByEmail("Yi@gmail.com"));
    }
}