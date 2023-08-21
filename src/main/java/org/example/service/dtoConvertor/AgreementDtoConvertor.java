package org.example.service.dtoConvertor;

import org.example.dto.AgreementDto;
import org.example.entities.Agreement;
import org.springframework.stereotype.Component;

@Component
public class AgreementDtoConvertor<ENTITY, DTO> implements Converter<Agreement, AgreementDto> {


    @Override
    public AgreementDto toDto(Agreement agreement) {
        return new AgreementDto(agreement.getSum());
    }

    @Override
    public Agreement toEntity(AgreementDto agreementDto) {
        return new Agreement(agreementDto.getSum());
    }
}
