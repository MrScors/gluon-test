package com.gluonhq.samples.notes.data.enums;

import lombok.Getter;

@Getter
public enum QueenPresence {

    ADDING_QUEEN("Queen found"),
    REMOVING_QUEEN("Eggs found"),
    EXPORTING_BROOD_FRAMES("Virgin found"),
    HARVESTING_HONEY("Queen hatched"),
    NONE("None"),
    UNKNOWN("Unknown");

    public final String textRepresentation;

    QueenPresence(String textRepresentation) {
        this.textRepresentation = textRepresentation;
    }

    public static QueenPresence fromTextRepresentation(String textRepresentation) {
        for (QueenPresence queenPresence : QueenPresence.values()) {
            if (queenPresence.textRepresentation.equals(textRepresentation)) {
                return queenPresence;
            }
        }
        throw new IllegalArgumentException("No enum constant with text representation " + textRepresentation);
    }

}