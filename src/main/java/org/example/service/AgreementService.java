package org.example.service;

import org.example.entities.Agreement;

import java.util.List;

public interface AgreementService {

    List<Agreement> getAll();

    Agreement getById(long agreementId);

    Agreement save(Agreement agreement);
}
