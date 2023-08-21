package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private int status;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updateAt;


    public Manager(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @OneToMany(mappedBy = "manager")
    @JsonIgnore
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "manager")
    private List<Product> products = new ArrayList<>();

}
