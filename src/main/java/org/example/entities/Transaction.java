package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long iban;


    private int type;

    private BigDecimal amount;


    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createAt;


    public Transaction(BigDecimal amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public Transaction(BigDecimal amount, Account debit_account, Account credit_account) {
        this.amount = amount;
        this.debitAccount = debit_account;
        this.creditAccount = credit_account;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "debit_account_id", referencedColumnName = "id")
    private Account debitAccount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_account_id", referencedColumnName = "id")
    private Account creditAccount;



}
