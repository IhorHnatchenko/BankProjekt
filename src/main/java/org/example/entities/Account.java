package org.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.enums.Currency;
import org.example.enums.Status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank
    private String name;
    private int type;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Currency currencyCode;

    public Account(long id, String name, BigDecimal balance, Status status) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.status = status;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientId", referencedColumnName = "id")
    private Client registeredClient;

    @OneToMany(mappedBy = "debitAccount")
    private List<Transaction> debit_transactions = new ArrayList<>();


    @OneToMany(mappedBy = "creditAccount")
    private List<Transaction> credit_transactions = new ArrayList<>();



}
