package com.infopulse.resumemanager.repository.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnglishLevel {
    BEGINNER("Beginner"),
    PRE_INTERMEDIATE("Pre Intermediate"),
    INTERMEDIATE("Intermediate"),
    UPPER_INTERMEDIATE("Upper Intermediate"),
    ADVANCED("Advanced"),
    PROFICIENT("Proficient");
    final private String value;
    EnglishLevel(String name) {
        value = name;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
