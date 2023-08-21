package org.example.service.dtoConvertor;

import org.example.dto.ManagerDto;
import org.example.entities.Manager;
import org.springframework.stereotype.Component;

@Component
public class ManagerDtoConvertor<ENTITY, DTO> implements Converter<Manager, ManagerDto> {

    @Override
    public ManagerDto toDto(Manager manager) {
        return new ManagerDto(manager.getFirstName(), manager.getLastName());
    }

    @Override
    public Manager toEntity(ManagerDto managerDto) {
        return new Manager(managerDto.getFirstName(), managerDto.getLastName());
    }
}
