package org.example.dto;

import lombok.*;
import org.example.enums.Status;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private long id;

    private String name;

    private BigDecimal balance;

    private Status status;
}
