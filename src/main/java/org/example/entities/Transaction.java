package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private int type;

    private BigDecimal amount;


    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createAt;


    public Transaction(BigDecimal amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public Transaction(BigDecimal amount, String description, Account debit_account, Account credit_account) {
        this.amount = amount;
        this.description = description;
        this.debitAccount = debit_account;
        this.creditAccount = credit_account;
    }

    // Проверить связи.
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "debit_account_id", referencedColumnName = "id")
    private Account debitAccount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_account_id", referencedColumnName = "id")
    private Account creditAccount;



}
