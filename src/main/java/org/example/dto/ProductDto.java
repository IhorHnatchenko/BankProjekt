package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Currency;
import org.example.enums.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private long id;

    private String name;

    private Currency currencyCode;

    private int limitDB;

    private Status status;
}
