package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long iban;

    private int type;

    private BigDecimal amount;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createAt;

    public Transaction(long iban, BigDecimal amount, String description) {
        this.iban = iban;
        this.amount = amount;
        this.description = description;
    }

    public Transaction(BigDecimal amount, Account debit_account, Account credit_account) {
        this.amount = amount;
        this.debitAccount = debit_account;
        this.creditAccount = credit_account;
    }

    public Transaction(
            BigDecimal amount,
            Account debit_account,
            Account credit_account,
            String description,
            Timestamp createAt) {
        this.amount = amount;
        this.debitAccount = debit_account;
        this.creditAccount = credit_account;
        this.description = description;
        this.createAt = createAt;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "debit_account_id", referencedColumnName = "id")
    private Account debitAccount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_account_id", referencedColumnName = "id")
    private Account creditAccount;
}
