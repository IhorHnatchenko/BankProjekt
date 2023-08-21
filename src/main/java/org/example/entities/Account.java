package org.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.enums.Currency;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int status;
    @NotBlank
    private String name;
    private int type;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Currency currencyCode;

    public Account(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientId", referencedColumnName = "id")
    private Client registeredClient;

    @OneToMany(mappedBy = "debitAccount")
    private List<Transaction> debit_transactions = new ArrayList<>();


    @OneToMany(mappedBy = "creditAccount")
    private List<Transaction> credit_transactions = new ArrayList<>();



}
