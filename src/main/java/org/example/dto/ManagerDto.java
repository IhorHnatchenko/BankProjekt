package org.example.dto;

import lombok.*;
import org.example.enums.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDto {

    private long id;

    private String firstName;

    private String lastName;

    private Status status;
}
