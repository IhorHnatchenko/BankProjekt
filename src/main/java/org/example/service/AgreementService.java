package org.example.service;

import org.example.entities.Agreement;
import org.example.enums.Status;
import java.util.List;

public interface AgreementService {

    List<Agreement> getAll();

    Agreement getById(long agreementId);

    Agreement save(Agreement agreement);

    Agreement updateStatus(long agreementId, Status status);
}
