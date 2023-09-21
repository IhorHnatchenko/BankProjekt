package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {

    ACTIVE("The status is active."),
    INACTIVE("The status is inactive.");

    private final String description;
}
