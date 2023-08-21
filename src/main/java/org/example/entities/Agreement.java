package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "agreement")
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private BigDecimal interestRate;

    private int status;

    private BigDecimal sum;

    public Agreement(BigDecimal sum) {
        this.sum = sum;
    }

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp create_at;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp update_at;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

}
