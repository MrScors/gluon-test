package com.gluonhq.samples.notes.data.enums;

import lombok.Getter;

@Getter
public enum ActionType {

    ADDING_QUEEN("Add Queen"),
    REMOVING_QUEEN("Remove Queen"),
    EXPORTING_BROOD_FRAMES("Export Brood"),
    HARVESTING_HONEY("Honey Harvest");

    public final String textRepresentation;

    ActionType(String textRepresentation) {
        this.textRepresentation = textRepresentation;
    }



}