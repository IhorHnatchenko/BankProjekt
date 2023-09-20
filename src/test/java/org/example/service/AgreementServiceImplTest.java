package org.example.service;

import org.example.entities.*;
import org.example.repository.AgreementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.enums.Currency.USD;
import static org.example.enums.Status.ACTIVE;
import static org.example.enums.Status.INACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementServiceImplTest {

    @Mock
    private AgreementRepository agreementRepository;

    @InjectMocks
    private AgreementServiceImpl agreementService;

    @Mock
    private AccountServiceImpl accountService;

    @Mock
    private ProductServiceImpl productService;

    @Test
    void getAll() {
        List<Agreement> agreements = new ArrayList<>();

        agreements.add(new Agreement(1L, BigDecimal.valueOf(20), ACTIVE));
        agreements.add(new Agreement(2L,BigDecimal.valueOf(15), ACTIVE));

        when(agreementRepository.findAll()).thenReturn(agreements);

        List<Agreement> savedAll = agreementService.getAll();

        assertEquals(agreements, savedAll);
    }

    @Test
    void getByIdWhenIdExists() {

        Agreement agreement = Agreement.builder()
                .id(1L)
                .sum(BigDecimal.valueOf(20))
                .status(ACTIVE).build();

        when(agreementRepository.findById(agreement.getId())).thenReturn(Optional.of(agreement));

        assertEquals(agreement, agreementService.getById(1L));
    }

    @Test
    void getByIdWhenIdNotExists() {

        Agreement agreement = Agreement.builder()
                .sum(BigDecimal.valueOf(20))
                .status(ACTIVE).build();

        when(agreementRepository.findById(agreement.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> agreementService.getById(agreement.getId()));
    }

    @Test
    void create() {

        Manager manager = Manager.builder()
                .id(1L)
                .status(ACTIVE)
                .firstName("Ihor")
                .lastName("Hnatchenko").build();

        Client client = Client.builder()
                .firstName("Alex")
                .lastName("Honkaj")
                .manager(manager).build();

        Account account = Account.builder()
                .name("Ihor")
                .balance(BigDecimal.valueOf(100))
                .status(ACTIVE)
                .registeredClient(client).build();

        when(accountService.getById(account.getId())).thenReturn(account);

        Product product = Product.builder()
                .name("creditOneForUSD")
                .currencyCode(USD)
                .limitDB(20)
                .status(ACTIVE)
                .manager(manager).build();

        when(productService.getById(product.getId())).thenReturn(product);

        Agreement agreement = Agreement.builder()
                .sum(BigDecimal.valueOf(20))
                .status(ACTIVE)
                .account(account)
                .product(product).build();

        when(agreementRepository.save(Mockito.any(Agreement.class))).thenReturn(agreement);

        assertEquals(agreement, agreementService.save(agreement));
    }

    @Test
    void updateStatus() {

        Agreement agreement = Agreement.builder()
                .sum(BigDecimal.valueOf(20))
                .status(ACTIVE).build();

        when(agreementRepository.findById(agreement.getId())).thenReturn(Optional.of(agreement));
        when(agreementRepository.save(Mockito.any(Agreement.class))).thenReturn(agreement);

        Agreement withUpdateStatus = agreementService.updateStatus(agreement.getId(), INACTIVE);

        assertEquals(INACTIVE, withUpdateStatus.getStatus());
    }
}