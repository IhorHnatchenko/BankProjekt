package org.example.service.dtoConvertor;

import org.example.dto.ClientDto;
import org.example.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientDtoConvertor<ENTITY, DTO> implements Converter<Client, ClientDto> {
    @Override
    public ClientDto toDto(Client client) {
        return new ClientDto(client.getFirstName(), client.getLastName());
    }

    @Override
    public Client toEntity(ClientDto clientDto) {
        return new Client(clientDto.getFirstName(), clientDto.getLastName());
    }
}
