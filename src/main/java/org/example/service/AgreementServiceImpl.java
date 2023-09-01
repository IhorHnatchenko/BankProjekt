package org.example.service;

import org.example.entities.Agreement;
import org.example.enums.Status;
import org.example.repository.AgreementRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AgreementServiceImpl implements AgreementService {

    private final AgreementRepository agreementRepository;

    public AgreementServiceImpl(AgreementRepository agreementRepository) {
        this.agreementRepository = agreementRepository;
    }


    @Override
    public List<Agreement> getAll() {
        return agreementRepository.findAll();
    }

    @Override
    public Agreement getById(long agreementId) {
        return agreementRepository.getReferenceById(agreementId);
    }

    @Override
    public Agreement save(Agreement agreement) {
        return agreementRepository.save(agreement);
    }

    @Override
    public Agreement updateStatus(long agreementId, Status status) {
        Agreement agreement = agreementRepository.getReferenceById(agreementId);

        agreement.setStatus(status);
        return agreementRepository.save(agreement);
    }
}
