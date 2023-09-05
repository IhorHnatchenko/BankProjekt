package org.example.service;

import org.example.entities.Account;
import org.example.entities.Agreement;
import org.example.entities.Product;
import org.example.enums.Status;
import org.example.repository.AccountRepository;
import org.example.repository.AgreementRepository;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AgreementServiceImpl implements AgreementService {

    private final AgreementRepository agreementRepository;

    private final AccountRepository accountRepository;

    private final ProductRepository productRepository;

    public AgreementServiceImpl(
            AgreementRepository agreementRepository,
            AccountRepository accountRepository,
            ProductRepository productRepository) {
        this.agreementRepository = agreementRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
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
        if(agreement.getAccount() != null && agreement.getProduct() != null &&
        agreement.getAccount().getId() != 0 && agreement.getProduct().getId() != 0){
            Account account = accountRepository.findById(agreement.getAccount().getId()).get();
            Product product = productRepository.findById(agreement.getProduct().getId()).get();
            agreement.setAccount(account);
            agreement.setProduct(product);
            return agreementRepository.save(agreement);
        }
        return null;
    }

    @Override
    public Agreement updateStatus(long agreementId, Status status) {
        Agreement agreement = agreementRepository.getReferenceById(agreementId);

        agreement.setStatus(status);
        return agreementRepository.save(agreement);
    }
}
