package org.example.entities;

import lombok.*;
import org.example.enums.Status;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Entity
@Table(name = "agreements")
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal interestRate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal sum;

    public Agreement(long id, BigDecimal sum, Status status) {
        this.id = id;
        this.sum = sum;
        this.status = status;
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
