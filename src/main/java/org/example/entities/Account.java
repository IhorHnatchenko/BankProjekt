package org.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private int currency_code;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp create_at;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp update_at;

    @Transient
    //@ManyToOne
    private Client client;

    @Transient
    //@ManyToOne
    private Agreement agreement;
}
