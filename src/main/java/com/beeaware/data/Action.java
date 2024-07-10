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
public class Action {
    private UUID id;
    private int hiveId;
    private String actionDate;
    private String actionType;

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("A.H");
        builder.append(hiveId);
        builder.append(".D");
        builder.append(actionDate);
        builder.append(".T");
        builder.append(actionType);
        return builder.toString();
    }
}