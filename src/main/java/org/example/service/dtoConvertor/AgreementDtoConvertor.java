package org.example.service.dtoConvertor;

import org.example.dto.AgreementDto;
import org.example.entities.Agreement;
import org.springframework.stereotype.Component;

@Component
public class AgreementDtoConvertor implements Converter<Agreement, AgreementDto> {

    @Override
    public AgreementDto toDto(Agreement agreement) {
        return new AgreementDto(agreement.getId(), agreement.getSum(), agreement.getStatus());
    }

    @Override
    public Agreement toEntity(AgreementDto agreementDto) {
        return new Agreement(agreementDto.getId(), agreementDto.getSum(), agreementDto.getStatus());
    }
}
