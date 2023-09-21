package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.ClientDto;
import org.example.dto.ManagerDto;
import org.example.entities.Client;
import org.example.entities.Manager;
import org.example.service.ClientService;
import org.example.service.dtoConvertor.ClientDtoConvertor;
import org.example.service.dtoConvertor.Converter;
import org.example.service.dtoConvertor.ManagerDtoConvertor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.example.enums.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest {

    @MockBean
    private ClientService<Client> clientService;

    @MockBean
    private ClientDtoConvertor converter;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ManagerDtoConvertor managerDtoConvertor;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll() throws Exception {
        Manager manager = new Manager(1L, ACTIVE, "Alex", "Alexeev");

        Client client = new Client(
                1L,
                "Ihor",
                "Hnatchenko",
                "Ihor@gmail.com",
                ACTIVE,
                "1234",
                manager);

        when(passwordEncoder.encode(client.getPassword())).thenReturn("ZGF0dG9yZUBnbWFpbC5jb206MTIzNA==");

        when(clientService.getAll()).thenReturn(List.of(client));

        when(managerDtoConvertor.toDto(manager)).thenReturn(new ManagerDto(manager.getId(), manager.getFirstName(),
                manager.getLastName(), manager.getStatus()));

        when(converter.toDto(client)).thenReturn(new ClientDto(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getStatus()));

        mockMvc.perform(MockMvcRequestBuilders.get("/clients").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(List.of(new ClientDto(
                        client.getId(),
                        client.getFirstName(),
                        client.getLastName(),
                        client.getEmail(),
                        client.getStatus()
                )))));
    }

    @Test
    void getById() {
    }

    @Test
    void getByEmail() {
    }

    @Test
    void save() {
    }

    @Test
    void updateStatus() {
    }

    @Test
    void changeManager() {
    }

    @Test
    void updatePhone() {
    }

    @Test
    void updateEmail() {
    }

    @Test
    void updateAddress() {
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}