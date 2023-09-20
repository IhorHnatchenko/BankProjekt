package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.ClientDto;
import org.example.entities.Client;
import org.example.service.ClientService;
import org.example.service.dtoConvertor.Converter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.example.enums.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @MockBean
    private ClientService<Client> clientService;

    @MockBean
    private Converter<Client, ClientDto> converter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll() throws Exception {
        Client client = new Client(
                1L,
                ACTIVE,
                "1234",
                "Ihor",
                "Hnatchenko",
                "Ihor@gmail.com",
                "1234",
                "hello",
                287787389);

        Mockito.when(clientService.getAll()).thenReturn(List.of(client));

        Mockito.when(converter.toDto(client)).thenReturn(new ClientDto(
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