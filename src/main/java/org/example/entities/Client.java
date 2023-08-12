package org.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private int status;
    //@NotBlank
    private String taxCode;
    //@NotBlank
    private String firstName;
    //@NotBlank
    private String lastName;
    //@NotBlank
    private String email;
    //@NotBlank
    private String address;
    //@NotBlank
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updateAt;


/*      Здесь соединение один к однму, мне кажется не корректрым.
     Так как если посмотреть со стороны менеджера, то менджер может иметь много клиентов,
     А это означает что со стороны клиента должно быть соединение многие к одному.
     Тоесть все клиенты определённого менеджера. */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager _id", referencedColumnName = "id")
    private Manager manager;

    @OneToMany
    @JoinColumn(name = "account_id")
    private List<Account> account = new ArrayList<>();
}
