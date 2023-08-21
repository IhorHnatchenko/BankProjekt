package org.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.enums.Currency;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    private int status;

    @Enumerated(EnumType.STRING)
    private Currency currencyCode;

    private BigDecimal interestRate;

    private int limitDB;

    private Timestamp createAt;
    private Timestamp updateAt;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "managerId", referencedColumnName = "id")
    private Manager manager;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agreementId", referencedColumnName = "id")
    private Agreement agreement;


    /*    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private UserProfile userProfile;
*/

    public Product(String name, Currency currencyCode, int limitDB) {
        this.name = name;
        this.currencyCode = currencyCode;
        this.limitDB = limitDB;
    }
}
