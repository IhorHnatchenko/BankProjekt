package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.enums.Status;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(unique = true)
    private String taxCode;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Column(unique = true)
    private int phone;

    private Timestamp createAt;

    private Timestamp updateAt;

    public Client(String firstName, String lastName, Manager manager) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.manager = manager;
    }

    public Client(String firstName, String lastName, Status status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Client(long id, String firstName, String lastName, String email, Status status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
        this.createAt = Timestamp.valueOf(LocalDateTime.now());
        this.updateAt = createAt;
    }

    public Client(long id, String firstName, String lastName, String email, Status status, String password, Manager manager) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
        this.password = password;
        this.createAt = Timestamp.valueOf(LocalDateTime.now());
        this.updateAt = createAt;
        this.manager = manager;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "managerId", referencedColumnName = "id")
    private Manager manager;

    @OneToMany(mappedBy = "registeredClient")
    @JsonIgnore
    private List<Account> accounts = new ArrayList<>();

    public Client(long id, Status status, String taxCode, String firstName, String lastName, String email, String password, String address, int phone) {
        this.id = id;
        this.status = status;
        this.taxCode = taxCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }
}
