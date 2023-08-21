package org.example.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private String name;

    private BigDecimal balance;
}
