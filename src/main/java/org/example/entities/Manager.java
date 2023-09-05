package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.enums.Status;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name = "managers")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updateAt;


    public Manager(Status status, String firstName, String lastName,  Timestamp createAt, Timestamp updateAt){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Manager(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @OneToMany(mappedBy = "manager")
    @JsonIgnore
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "manager")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

}
