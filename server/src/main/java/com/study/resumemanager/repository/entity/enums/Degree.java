package com.study.resumemanager.repository.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Degree {
    NONE("None"),
    BACHELOR("Bachelor"),
    MASTER("Master"),
    DOCTOR("Doctor");
    final private String value;
    Degree(String name) {
        value = name;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
