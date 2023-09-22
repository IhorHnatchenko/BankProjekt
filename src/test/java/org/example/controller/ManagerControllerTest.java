package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.ManagerDto;
import org.example.entities.Manager;
import org.example.exceptions.BadArgumentsException;
import org.example.service.ManagerService;
import org.example.service.dtoConvertor.ManagerDtoConvertor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.example.enums.Status.INACTIVE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import java.util.List;

import static org.example.enums.Status.ACTIVE;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ManagerController.class)
@AutoConfigureMockMvc(addFilters = false)
class ManagerControllerTest {

    @MockBean
    private ManagerService<Manager> managerService;

    @MockBean
    private ManagerDtoConvertor converter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll() throws Exception {
        Manager manager = new Manager(1L, ACTIVE, "Alex", "Alexeev");

        when(managerService.getAll()).thenReturn(List.of(manager));

        ManagerDto managerDto = new ManagerDto(manager.getId(), manager.getFirstName(),
                manager.getLastName(), manager.getStatus());

        when(converter.toDto(manager)).thenReturn(managerDto);

        mockMvc.perform(get("/managers").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(List.of(managerDto))));
    }

    @Test
    void getByIdWhenIdIsExists() throws Exception {
        Manager manager = new Manager(1L, ACTIVE, "Alex", "Alexeev");

        when(managerService.getById(manager.getId())).thenReturn(manager);

        ManagerDto managerDto = new ManagerDto(manager.getId(), manager.getFirstName(),
                manager.getLastName(), manager.getStatus());


        when(converter.toDto(manager)).thenReturn(managerDto);

        mockMvc.perform(get("/managers/" + manager.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(managerDto)));
    }

    @Test
    void getByIdWhenIdIsNotExists() throws Exception{

        Manager notExistsManager = new Manager(1L, ACTIVE, "Alex", "Alexeev");

        when(managerService.getById(notExistsManager.getId())).thenThrow(BadArgumentsException.class);

        mockMvc.perform(get("/managers/" + notExistsManager.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException));
    }

    @Test
    void createClient() throws Exception {
        Manager manager = new Manager(ACTIVE, "Alex", "Alexeev");

        when(managerService.save(manager)).thenReturn(manager);

        mockMvc.perform(post("/managers/")
                        .content(asJsonString(manager))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated()) // Ожидаем HTTP 201 код
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.firstName").value("Alex"))
                .andExpect(jsonPath("$.lastName").value("Alexeev"));
    }

    @Test
    void updateStatus() throws Exception {
        ManagerDto managerDto = new ManagerDto(1L, "Alex", "Alexeev", ACTIVE);

        Manager manager = new Manager(1L, "Alex", "Alexeev", ACTIVE);

        when(converter.toEntity(managerDto)).thenReturn(manager);

        when(managerService.updateStatus(manager.getId(), INACTIVE)).thenReturn(manager);

        when(converter.toDto(manager)).thenReturn(new ManagerDto(manager.getId(), manager.getFirstName(),
                manager.getLastName(), manager.getStatus()));

        mockMvc.perform(put("/managers/update/status/{id}", 1L)
                        .content(asJsonString(manager))
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