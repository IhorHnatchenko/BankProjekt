package org.example.enums;

public enum Status {

    ACTIVE ("The status is active."),
    INACTIVE ("The status is inactive.");

    private String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
