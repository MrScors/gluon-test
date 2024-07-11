package com.gluonhq.samples.notes.data;

import com.gluonhq.samples.notes.data.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inspection {
    private String id;
    private int hiveId;
    private long inspectionDate;
    private StateOfBrood stateOfBrood;
    private QueenPresence queenPresence;
    private ChalkBroodAmount amountOfChalkBrood;
    private SwarmCells swarmCells;
    private AngerLevel angerLevel;
    List<ActionType> actionsList;

    public Inspection(int hiveId){
        this.id = UUID.randomUUID().toString();
        this.hiveId = hiveId;
    }

}