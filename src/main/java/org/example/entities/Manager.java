package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

    private Timestamp createAt;

    private Timestamp updateAt;

    public Manager(Status status, String firstName, String lastName) {
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Manager(long id, Status status, String firstName, String lastName) {
        this.id = id;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Manager(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Manager(long id, String firstName, String lastName, Status status) {
        this.id = id;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createAt = Timestamp.valueOf(LocalDateTime.now());
        this.updateAt = createAt;
    }

    @OneToMany(mappedBy = "manager")
    @JsonIgnore
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "manager")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();
}
