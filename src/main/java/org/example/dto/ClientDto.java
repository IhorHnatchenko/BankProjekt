package org.example.dto;

import lombok.*;
import org.example.enums.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private Status status;
}
