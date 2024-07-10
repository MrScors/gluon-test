package com.beeaware.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Hive {
    private int id;
    private List<Integer> occupiedBoxes;
    private long creationDate;

    public Hive() {
        this.creationDate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("H");
        builder.append(id);
        builder.append(".B");
        occupiedBoxes.forEach(id -> {
            builder.append(" ").append(id);
        });
        return builder.toString();
    }
}