package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.enums.Status;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "agreements")
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private BigDecimal interestRate;

    @Enumerated(EnumType.STRING)
    private Status status;

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

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

}
