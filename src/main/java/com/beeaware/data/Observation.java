package com.beeaware.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Observation {
    private UUID id;
    private int hiveId;
    private String observationDate;
    private String stateOfBrood;
    private String queenPresence;
    private String amountOfChalkbrood;
    private String swarmSupersedureCells;
    private String angerLevel;

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("O.H");
        builder.append(hiveId);
        builder.append(".D");
        builder.append(observationDate);
        builder.append(".B");
        builder.append(stateOfBrood);
        builder.append(".Q");
        builder.append(queenPresence);
        builder.append(".C");
        builder.append(amountOfChalkbrood);
        builder.append(".S");
        builder.append(swarmSupersedureCells);
        builder.append(".A");
        builder.append(angerLevel);
        return builder.toString();
    }
}