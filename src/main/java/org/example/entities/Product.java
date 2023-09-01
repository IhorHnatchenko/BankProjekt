package org.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.enums.Currency;
import org.example.enums.Status;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Currency currencyCode;

    private BigDecimal interestRate;

    @Column(name = "limit_db")
    private int limitDB;

    private Timestamp createAt;
    private Timestamp updateAt;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_manager_id", referencedColumnName = "id")
    private Manager manager;

    public Product(String name, Currency currencyCode, int limitDB) {
        this.name = name;
        this.currencyCode = currencyCode;
        this.limitDB = limitDB;
    }

/*    {
        "name": "credit1",
            "status": 1,
            "currencyCode": "USD",
            "interestRate": 67,
            "limitDB": 12
    }*/
}
