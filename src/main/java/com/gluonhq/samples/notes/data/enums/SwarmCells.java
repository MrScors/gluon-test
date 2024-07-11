package com.gluonhq.samples.notes.data.enums;

import lombok.Getter;

@Getter
public enum SwarmCells {

    SWARM_CELLS("Swarm Cells"),
    SUPERSEDURE_CELLS("Supersedure cells"),
    BOTH("Both"),
    NONE("None"),
    UNKNOWN("Unknown");

    public final String textRepresentation;

    SwarmCells(String textRepresentation) {
        this.textRepresentation = textRepresentation;
    }

    public static SwarmCells fromTextRepresentation(String textRepresentation) {
        for (SwarmCells swarmCells : SwarmCells.values()) {
            if (swarmCells.textRepresentation.equals(textRepresentation)) {
                return swarmCells;
            }
        }
        throw new IllegalArgumentException("No enum constant with text representation " + textRepresentation);
    }

}