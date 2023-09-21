package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {

    USD(840, "US Dollar"),
    EUR(978, "Euro"),
    GBP(826, "British Pound"),
    JPY(392, "Japanese Yen"),
    CAD(124, "Canadian Dollar"),
    AUD(36, "Australian Dollar"),
    CHF(756, "Swiss Franc"),
    CNY(156, "Chinese Yuan"),
    INR(356, "Indian Rupee"),
    RUB(643, "Russian Ruble"),
    UAH(980, "Ukrainian Hryvnia");

    private final int code;
    private final String description;
}
