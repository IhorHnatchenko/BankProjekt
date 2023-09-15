package org.example.service;

import org.example.entities.Agreement;
import org.example.enums.Status;
import java.util.List;

public interface AgreementService<AG> {

    List<AG> getAll();

    AG getById(long agreementId);

    AG save(AG agreement);

    AG updateStatus(long agreementId, Status status);
}
