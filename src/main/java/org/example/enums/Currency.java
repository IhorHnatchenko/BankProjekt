package org.example.enums;

public enum Currency {

    USD(840, "US Dollar"),
    EUR(978, "Euro"),
    GBP(826, "British Pound"),
    JPY(392, "Japanese Yen"),
    CAD(124, "Canadian Dollar"),
    AUD(036, "Australian Dollar"),
    CHF(756, "Swiss Franc"),
    CNY(156, "Chinese Yuan"),
    INR(356, "Indian Rupee"),
    RUB(643, "Russian Ruble"),
    UAH(980, "Ukrainian Hryvnia");

    private int code;
    private String description;

    Currency(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
