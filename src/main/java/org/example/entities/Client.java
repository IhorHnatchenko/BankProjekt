package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int status;

    @Column(unique = true)
    private String taxCode;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String address;

    @Column(unique = true)
    private int phone;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updateAt;

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "managerId", referencedColumnName = "id")
    private Manager manager;


    @OneToMany(mappedBy = "registeredClient")
    private List<Account> accounts = new ArrayList<>();

}
