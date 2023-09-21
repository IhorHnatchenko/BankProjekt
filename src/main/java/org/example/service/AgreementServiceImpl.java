package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entities.Account;
import org.example.entities.Agreement;
import org.example.entities.Product;
import org.example.enums.Status;
import org.example.repository.AgreementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService<Agreement> {

    private final AgreementRepository agreementRepository;

    private final AccountService<Account> accountService;

    private final ProductService<Product> productService;

    @Override
    public List<Agreement> getAll() {
        return agreementRepository.findAll();
    }

    @Override
    public Agreement getById(long agreementId) {
        return agreementRepository
                .findById(agreementId)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect agreement id " + agreementId));
    }

    @Override
    public Agreement save(Agreement agreement) {

        Account account = accountService.getById(agreement.getAccount().getId());
        agreement.setAccount(account);
        Product product = productService.getById(agreement.getProduct().getId());
        agreement.setProduct(product);

        return agreementRepository.save(agreement);
    }

    @Override
    public Agreement updateStatus(long agreementId, Status status) {
        Agreement agreement = getById(agreementId);
        agreement.setStatus(status);

        return agreementRepository.save(agreement);
    }
}
