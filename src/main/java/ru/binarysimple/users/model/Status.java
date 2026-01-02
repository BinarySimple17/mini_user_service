package ru.binarysimple.users.model;

import lombok.Getter;

@Getter
public enum Status {
    INITIAL(null),
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
