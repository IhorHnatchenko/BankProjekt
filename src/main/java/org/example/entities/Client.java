package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.enums.Status;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;



@Data
@NoArgsConstructor
@EqualsAndHashCode
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
    @JsonIgnore
    private List<Account> accounts = new ArrayList<>();

}
