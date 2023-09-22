package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.ClientDto;
import org.example.entities.Client;
import org.example.entities.Manager;
import org.example.exceptions.BadArgumentsException;
import org.example.service.ClientService;
import org.example.service.dtoConvertor.ClientDtoConvertor;
import org.example.service.dtoConvertor.ManagerDtoConvertor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.example.enums.Status.INACTIVE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import static org.example.enums.Status.ACTIVE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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

        Client client = new Client(1L, "Ihor", "Hnatchenko",
                "Ihor@gmail.com", ACTIVE);
        when(clientService.getAll()).thenReturn(List.of(client));
        ClientDto clientDto = new ClientDto(client.getId(), client.getFirstName(),
                client.getLastName(), client.getEmail(), client.getStatus());
        when(converter.toDto(client)).thenReturn(clientDto);

        mockMvc.perform(get("/clients").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(List.of(clientDto))));
    }

    @Test
    void getByIdWhenIdIsExists() throws Exception {

        Client client = new Client(1L, "Ihor", "Hnatchenko",
                "Ihor@gmail.com", ACTIVE);

        when(clientService.getById(client.getId())).thenReturn(client);

        ClientDto clientDto = new ClientDto(client.getId(), client.getFirstName(),
                client.getLastName(), client.getEmail(), client.getStatus());

        when(converter.toDto(client)).thenReturn(clientDto);

        mockMvc.perform(get("/clients/" + client.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(clientDto)));
    }

    @Test
    void getByIdWhenIdIsNotExists() throws Exception {

        Client notExistsClient = new Client(1L, "Ihor", "Hnatchenko",
                "Ihor@gmail.com", ACTIVE);

        when(clientService.getById(notExistsClient.getId())).thenThrow(BadArgumentsException.class);

        mockMvc.perform(get("/clients/" + notExistsClient.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException));
    }

    @Test
    void getByEmail() throws Exception {

        Client client = new Client(1L, "Ihor", "Hnatchenko",
                "Ihor@gmail.com", ACTIVE);

        when(clientService.getByEmail(client.getEmail())).thenReturn(client);

        ClientDto clientDto = new ClientDto(client.getId(), client.getFirstName(),
                client.getLastName(), client.getEmail(), client.getStatus());

        when(converter.toDto(client)).thenReturn(clientDto);

        mockMvc.perform(get("/clients/email/" + client.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(clientDto)));

    }

    @Test
    void createClient() throws Exception {
        Client client = new Client("Ihor", "Hnatchenko", ACTIVE);

        when(clientService.save(client)).thenReturn(client);

        mockMvc.perform(post("/clients/")
                        .content(asJsonString(client))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Ihor"))
                .andExpect(jsonPath("$.lastName").value("Hnatchenko"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

    }

    @Test
    void updateStatus() throws Exception {

        ClientDto clientDto = new ClientDto(1L, "Ihor", "Hnatchenko",
                "Ihor@gmail.com", ACTIVE);

        Client client = new Client(1L, "Ihor", "Hnatchenko",
                "Ihor@gmail.com", ACTIVE);

        when(converter.toEntity(clientDto)).thenReturn(client);

        when(clientService.updateStatus(client.getId(), INACTIVE)).thenReturn(client);

        when(converter.toDto(client)).thenReturn(new ClientDto(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getStatus()
        ));

        mockMvc.perform(put("/clients/update/status/{id}", 1L)
                        .content(asJsonString(client))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void changeManager() throws Exception {

        Manager manager = new Manager(1L, "Alex", "Alexeev", ACTIVE);

        ClientDto clientDto = new ClientDto(1L, "Ihor", "Hnatchenko",
                "Ihor@gmail.com", ACTIVE);

        Client client = new Client(1L, "Ihor", "Hnatchenko",
                "Ihor@gmail.com", ACTIVE, "1234", manager);

        when(converter.toEntity(clientDto)).thenReturn(client);

        when(clientService.getById(client.getId())).thenReturn(client);

        when(clientService.changeManager(client.getId(), manager)).thenReturn(client);

        when(converter.toDto(client)).thenReturn(new ClientDto(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getStatus()
        ));

        mockMvc.perform(put("/clients/change/manager/{id}", 1L)
                        .content(asJsonString(client))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updatePhone() throws Exception {

        ClientDto clientDto = new ClientDto(1L, "Ihor", "Hnatchenko",
                "Ihor@gmail.com", ACTIVE);

        Client client = new Client(1L, ACTIVE, "1221", "Ihor", "Hnatchenko",
                "Ihor@gmail.com", "1234", "hello", 287837889);

        when(converter.toEntity(clientDto)).thenReturn(client);

        when(clientService.updatePhoneNumber(client.getId(), 509814194)).thenReturn(client);

        when(converter.toDto(client)).thenReturn(new ClientDto(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getStatus()
        ));

        mockMvc.perform(put("/clients/update/phone/{id}", 1L)
                        .content(asJsonString(client))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateEmail() throws Exception {

        ClientDto clientDto = new ClientDto(1L, "Ihor", "Hnatchenko",
                "Ihor@gmail.com", ACTIVE);

        Client client = new Client(1L, ACTIVE, "1221", "Ihor", "Hnatchenko",
                "Ihor@gmail.com", "1234", "hello", 287837889);

        when(converter.toEntity(clientDto)).thenReturn(client);

        when(clientService.changeEmail(client.getId(), "Hnat@gmail.com")).thenReturn(client);

        when(converter.toDto(client)).thenReturn(new ClientDto(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getStatus()
        ));

        mockMvc.perform(put("/clients/change/email/{id}", 1L)
                        .content(asJsonString(client))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateAddress() throws Exception {

        ClientDto clientDto = new ClientDto(1L, "Ihor", "Hnatchenko",
                "Ihor@gmail.com", ACTIVE);

        Client client = new Client(1L, ACTIVE, "1221", "Ihor", "Hnatchenko",
                "Ihor@gmail.com", "1234", "hello", 287837889);

        when(converter.toEntity(clientDto)).thenReturn(client);

        when(clientService.changeEmail(client.getId(), "Hello")).thenReturn(client);

        when(converter.toDto(client)).thenReturn(new ClientDto(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getStatus()
        ));

        mockMvc.perform(put("/clients/change/address/{id}", 1L)
                        .content(asJsonString(client))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}