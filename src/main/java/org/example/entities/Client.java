package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.enums.Status;

import jakarta.persistence.*;
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

//    @Column(name = "firstName")
    private String firstName;

    private String lastName;

    // Use as login.
    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Column(unique = true)
    private int phone;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updateAt;

    public Client(String firstName, String lastName, Manager manager) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.manager = manager;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "managerId", referencedColumnName = "id")
    private Manager manager;


    @OneToMany(mappedBy = "registeredClient")
    @JsonIgnore
    private List<Account> accounts = new ArrayList<>();

}
