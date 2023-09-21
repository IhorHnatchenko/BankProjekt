package org.example.service.dtoConvertor;

import org.example.dto.ClientDto;
import org.example.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientDtoConvertor implements Converter<Client, ClientDto> {
    @Override
    public ClientDto toDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getStatus());
    }

    @Override
    public Client toEntity(ClientDto clientDto) {
        return new Client(
                clientDto.getId(),
                clientDto.getFirstName(),
                clientDto.getLastName(),
                clientDto.getEmail(),
                clientDto.getStatus());
    }
}
