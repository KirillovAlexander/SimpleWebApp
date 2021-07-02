package com.alexkirillov.simplewebapp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MALE,
    FEMALE;

    @JsonCreator
    public static Gender fromString(String value) {
        for(Gender type : Gender.values()) {
            if(type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}
