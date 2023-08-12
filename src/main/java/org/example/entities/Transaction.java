package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
    private Timestamp create_at;

    /*
    Я понял что одна тронзакция имеет одни конкретный счёт, но если смотреть со стороны счёта,
    то одни конкрентый счёт может иметь много транзакций.
    Означает ли это что в таблице транзакций, нам нужно сделать соединение одни к моногому?
     */
    @OneToOne
    @JoinColumn(name = "debit_account_id", referencedColumnName = "id")
    private Account debit_account;

    @OneToOne
    @JoinColumn(name = "credit_account_id", referencedColumnName = "id")
    private Account credit_account;
}
