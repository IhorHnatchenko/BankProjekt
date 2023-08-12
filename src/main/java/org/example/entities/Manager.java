package org.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private int status;
    @NotBlank
    private String first_name;
    @NotBlank
    private String last_name;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp create_at;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp update_at;

    //@OneToMany
    @Transient
    //@JoinColumn(name = "product_id")
    private List<Product> product;

    /*
        Здесь по идее должно быть ещё одно соединение один к многим к клиентам.
     */
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
//    @JoinColumn(name = "client_id")
    private List<Client> client;


}
