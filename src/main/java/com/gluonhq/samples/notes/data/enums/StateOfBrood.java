package com.gluonhq.samples.notes.data.enums;

import lombok.Getter;

@Getter
public enum StateOfBrood {

    DRONE_LAYER("Drone-layer"),
    LAYING_WORKERS("Laying Workers"),
    GREAT("Great Brood Quality"),
    NORMAL("Normal Brood"),
    POOR("Poor Brood Amount"),
    BAD("Bad Brood Amount"),
    UNKNOWN("Unknown");

    public final String textRepresentation;

    StateOfBrood(String textRepresentation) {
        this.textRepresentation = textRepresentation;
    }

    public static StateOfBrood fromTextRepresentation(String textRepresentation) {
        for (StateOfBrood stateOfBrood : StateOfBrood.values()) {
            if (stateOfBrood.textRepresentation.equals(textRepresentation)) {
                return stateOfBrood;
            }
        }
        throw new IllegalArgumentException("No enum constant with text representation " + textRepresentation);
    }


}