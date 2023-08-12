package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "agreement")
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private BigDecimal interest_rate;

    private int status;

    private BigDecimal sum;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp create_at;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp update_at;

    @OneToMany
    @JoinColumn(name = "account_id")
    private List<Account> account;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
