package com.gluonhq.samples.notes.data.enums;

import lombok.Getter;

@Getter
public enum ChalkBroodAmount {

    LOTS_OF("Lots of"),
    LITTLE_OF("Little of"),
    NONE("None"),
    UNKNOWN("Unknown");

    public final String textRepresentation;

    ChalkBroodAmount(String textRepresentation) {
        this.textRepresentation = textRepresentation;
    }

    public static ChalkBroodAmount fromTextRepresentation(String textRepresentation) {
        for (ChalkBroodAmount chalkBroodAmount : ChalkBroodAmount.values()) {
            if (chalkBroodAmount.textRepresentation.equals(textRepresentation)) {
                return chalkBroodAmount;
            }
        }
        throw new IllegalArgumentException("No enum constant with text representation " + textRepresentation);
    }
}