package com.infopulse.resumemanager.repository.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Level {
    DEFAULT("Default"),
    TRAINEE("Trainee"),
    JUNIOR("Junior"),
    MIDDLE("Middle"),
    SENIOR("Senior");
    final private String value;
    Level(String name) {
        value = name;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
