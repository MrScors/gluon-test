/*
 * Copyright (c) 2016, 2020, Gluon
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of Gluon, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL GLUON BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.gluonhq.samples.notes.service;

import com.gluonhq.cloudlink.client.data.DataClient;
import com.gluonhq.cloudlink.client.data.DataClientBuilder;
import com.gluonhq.cloudlink.client.data.OperationMode;
import com.gluonhq.cloudlink.client.data.SyncFlag;
import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.ConnectState;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.samples.notes.data.Hive;
import com.gluonhq.samples.notes.data.Inspection;
import com.gluonhq.samples.notes.data.Note;
import com.gluonhq.samples.notes.data.Settings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import lombok.Getter;

import javax.annotation.PostConstruct;
import java.util.Comparator;

public class Service {
    
    private static final String NOTES = "notes-v1";
    private static final String HIVES = "hives-v1";
    private static final String INSPECTIONS = "inspections-v1";
    private static final String NOTES_SETTINGS = "notes-settings-v1";
    
    private final ListProperty<Note> notes = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<Hive> hives = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<Inspection> inspections = new SimpleListProperty<>(FXCollections.observableArrayList());

    private final ObjectProperty<Settings> settings = new SimpleObjectProperty<>(new Settings());
    
    private DataClient dataClient;

    @Getter
    private static final Service instance = new Service();
    
    {
        dataClient = DataClientBuilder.create()
                .operationMode(OperationMode.LOCAL_ONLY)
//                .operationMode(OperationMode.CLOUD_ONLY) // Requires CloudLink account
                .build();
    }
    
    public void retrieveNotes() {
        GluonObservableList<Note> gluonNotes = DataProvider.retrieveList(
                dataClient.createListDataReader(NOTES, Note.class,
                SyncFlag.LIST_WRITE_THROUGH, SyncFlag.OBJECT_WRITE_THROUGH));
        
        gluonNotes.stateProperty().addListener((obs, ov, nv) -> {
            if (ConnectState.SUCCEEDED.equals(nv)) {
                notes.set(gluonNotes);
            }
        });
    }
    
    public Note addNote(Note note) {
        notes.get().add(note);
        return note;
    }

    public void removeNote(Note note) {
        notes.get().remove(note);
    }

    public ListProperty<Note> notesProperty() {
        return notes;
    }

    public void retrieveHives() {
        GluonObservableList<Hive> retrievedHives = DataProvider.retrieveList(
                dataClient.createListDataReader(HIVES, Hive.class,
                        SyncFlag.LIST_WRITE_THROUGH, SyncFlag.OBJECT_WRITE_THROUGH));

        retrievedHives.stateProperty().addListener((obs, ov, nv) -> {
            if (ConnectState.SUCCEEDED.equals(nv)) {
                hives.set(retrievedHives);
                retrieveSettings();
            }
        });
    }

    public Hive addHive(Hive hive) {
        if(hive.getId() == 0 && !hives.isEmpty()){
            hives.sort(Comparator.comparingInt(Hive::getId));
            hive.setId(hives.get(hives.size() - 1).getId() + 1);
        }
        hives.get().add(hive);
        return hive;
    }

    public void removeHive(Hive hive) {
        hives.get().remove(hive);
    }

    public ListProperty<Hive> hivesProperty() {
        return hives;
    }


    public void retrieveInspections() {
        GluonObservableList<Inspection> retrievedInspections = DataProvider.retrieveList(
                dataClient.createListDataReader(INSPECTIONS, Inspection.class,
                        SyncFlag.LIST_WRITE_THROUGH, SyncFlag.OBJECT_WRITE_THROUGH));

        retrievedInspections.stateProperty().addListener((obs, ov, nv) -> {
            if (ConnectState.SUCCEEDED.equals(nv)) {
                inspections.set(retrievedInspections);
                retrieveSettings();
            }
        });
    }

    public Inspection addInspection(Inspection inspection) {
        inspections.get().add(inspection);
        return inspection;
    }

    public void removeInspection(Inspection inspection) {
        inspections.get().remove(inspection);
    }

    public ListProperty<Inspection> inspectionsProperty() {
        return inspections;
    }


    private void retrieveSettings() {
        GluonObservableObject<Settings> gluonSettings = DataProvider.retrieveObject(
                dataClient.createObjectDataReader(NOTES_SETTINGS, Settings.class));
        gluonSettings.stateProperty().addListener((obs, ov, nv) -> {
            if (ConnectState.SUCCEEDED.equals(nv) && gluonSettings.get() != null) {
                settings.set(gluonSettings.get());
            }
        });
    }
    
    public void storeSettings() {
        DataProvider.storeObject(settings.get(),
                dataClient.createObjectDataWriter(NOTES_SETTINGS, Settings.class));
    }
    
    public ObjectProperty<Settings> settingsProperty() {
        return settings;
    }
    
}
