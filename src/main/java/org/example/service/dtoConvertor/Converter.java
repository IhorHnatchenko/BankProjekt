package org.example.service.dtoConvertor;

public interface Converter<ENTITY, DTO> {

    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);
}