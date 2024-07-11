package com.gluonhq.samples.notes.data.enums;

import lombok.Getter;

@Getter
public enum AngerLevel {

    ANGRY("Angry"),
    MODERATE("Moderate"),
    PEACEFUL("Peaceful"),
    UNKNOWN("Unknown");

    public final String textRepresentation;

    AngerLevel(String textRepresentation) {
        this.textRepresentation = textRepresentation;
    }

    public static AngerLevel fromTextRepresentation(String textRepresentation) {
        for (AngerLevel angerLevel : AngerLevel.values()) {
            if (angerLevel.textRepresentation.equals(textRepresentation)) {
                return angerLevel;
            }
        }
        throw new IllegalArgumentException("No enum constant with text representation " + textRepresentation);
    }

}