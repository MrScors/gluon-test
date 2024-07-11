/*
 * Copyright (c) 2016, 2021, Gluon
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
package com.gluonhq.samples.notes.views.edit;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.samples.notes.data.enums.*;
import javafx.scene.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.samples.notes.data.Hive;
import com.gluonhq.samples.notes.data.Inspection;
import com.gluonhq.samples.notes.data.model.ModelHive;
import com.gluonhq.samples.notes.data.model.ModelInspection;
import com.gluonhq.samples.notes.service.Service;
import com.gluonhq.samples.notes.views.AppViewManager;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static com.gluonhq.samples.notes.Utils.formatList;

public class EditInspectionPresenter {

    @Inject private Service service;

    @Inject private ModelInspection modelInspection;

    @FXML private View edition;

    @FXML private Button submit;
    @FXML private Button cancel;
    @FXML private TextField hiveId;
    @FXML private ChoiceBox<String> stateOfBrood;
    @FXML private ChoiceBox<String> queenPresence;
    @FXML private ChoiceBox<String> amountOfChalkBrood;
    @FXML private ChoiceBox<String> swarmCells;
    @FXML private ChoiceBox<String> angerLevel;
    @FXML private CheckBox addingQueenAction;
    @FXML private CheckBox removingQueenAction;
    @FXML private CheckBox exportingBroodFramesAction;
    @FXML private CheckBox harvestingHoneyAction;

    @FXML private ResourceBundle resources;

    private boolean editMode;

    public void initialize() {
//        edition.setShowTransitionFactory(BounceInRightTransition::new);

        edition.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                submit.disableProperty().unbind();
                
                Inspection activeInspection = modelInspection.getActiveInspection().get();
                if (activeInspection != null) {
                    submit.setText(resources.getString("button.submit.text"));
                    stateOfBrood.setValue(activeInspection.getStateOfBrood().textRepresentation);
                    queenPresence.setValue(activeInspection.getQueenPresence().textRepresentation);
                    amountOfChalkBrood.setValue(activeInspection.getAmountOfChalkBrood().textRepresentation);
                    swarmCells.setValue(activeInspection.getSwarmCells().textRepresentation);
                    angerLevel.setValue(activeInspection.getAngerLevel().textRepresentation);
                    addingQueenAction.setSelected(activeInspection.getActionsList().contains(ActionType.ADDING_QUEEN));
                    removingQueenAction.setSelected(activeInspection.getActionsList().contains(ActionType.REMOVING_QUEEN));
                    exportingBroodFramesAction.setSelected(activeInspection.getActionsList().contains(ActionType.EXPORTING_BROOD_FRAMES));
                    harvestingHoneyAction.setSelected(activeInspection.getActionsList().contains(ActionType.HARVESTING_HONEY));


                    // Enable submit button if user input differs from activeInspection
                    submit.disableProperty().bind(Bindings.createBooleanBinding(() -> {
                        boolean stateOfBroodChanged = stateOfBrood.getValue().equals(activeInspection.getStateOfBrood().textRepresentation);
                        boolean queenPresenceChanged = queenPresence.getValue().equals(activeInspection.getQueenPresence().textRepresentation);
                        boolean amountOfChalkBroodChanged = amountOfChalkBrood.getValue().equals(activeInspection.getAmountOfChalkBrood().textRepresentation);
                        boolean swarmCellsChanged = swarmCells.getValue().equals(activeInspection.getSwarmCells().textRepresentation);
                        boolean angerLevelChanged = angerLevel.getValue().equals(activeInspection.getAngerLevel().textRepresentation);

                        boolean addingQueenActionChanged = addingQueenAction.isSelected() ==
                                activeInspection.getActionsList().contains(ActionType.ADDING_QUEEN);
                        boolean removingQueenActionChanged = removingQueenAction.isSelected() ==
                                activeInspection.getActionsList().contains(ActionType.REMOVING_QUEEN);
                        boolean exportingBroodFramesActionChanged = exportingBroodFramesAction.isSelected() ==
                                activeInspection.getActionsList().contains(ActionType.EXPORTING_BROOD_FRAMES);
                        boolean harvestingHoneyActionChanged = harvestingHoneyAction.isSelected() ==
                                activeInspection.getActionsList().contains(ActionType.HARVESTING_HONEY);

                        return stateOfBroodChanged && queenPresenceChanged &&
                                amountOfChalkBroodChanged && swarmCellsChanged &&
                                angerLevelChanged && addingQueenActionChanged &&
                                removingQueenActionChanged && exportingBroodFramesActionChanged && harvestingHoneyActionChanged;
                    }, stateOfBrood.valueProperty(), queenPresence.valueProperty(),
                            amountOfChalkBrood.valueProperty(), swarmCells.valueProperty(),
                            angerLevel.valueProperty(), addingQueenAction.selectedProperty(),
                            removingQueenAction.selectedProperty(), exportingBroodFramesAction.selectedProperty(),
                            harvestingHoneyAction.selectedProperty()));

                    editMode = true;
                } else {
                    addingQueenAction.setSelected(false);
                    removingQueenAction.setSelected(false);
                    exportingBroodFramesAction.setSelected(false);
                    harvestingHoneyAction.setSelected(false);

                    stateOfBrood.setValue("Unknown");
                    queenPresence.setValue("Unknown");
                    amountOfChalkBrood.setValue("Unknown");
                    swarmCells.setValue("Unknown");
                    angerLevel.setValue("Unknown");

                    submit.setText(resources.getString("button.submit.text"));

                    editMode = false;
                }
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText(resources.getString(editMode ? "appbar.title.edit" : "appbar.title.add"));
            } else {
                hiveId.setText("");
                addingQueenAction.setSelected(false);
                removingQueenAction.setSelected(false);
                exportingBroodFramesAction.setSelected(false);
                harvestingHoneyAction.setSelected(false);

                stateOfBrood.setValue("Unknown");
                queenPresence.setValue("Unknown");
                amountOfChalkBrood.setValue("Unknown");
                swarmCells.setValue("Unknown");
                angerLevel.setValue("Unknown");
            }
        });
        
        submit.setOnAction(e -> {
            System.err.println("EDIT MODE:::");
            System.err.println(editMode);
            Inspection inspection = editMode ? modelInspection.getActiveInspection().get() : new Inspection(Integer.parseInt(hiveId.getText()));
            System.err.println(inspection.getHiveId());
            System.err.println(inspection.getId());

            if(inspection.getId() == null) inspection.setId(UUID.randomUUID().toString());

            inspection.setInspectionDate(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            inspection.setStateOfBrood(StateOfBrood.fromTextRepresentation(stateOfBrood.getValue()));
            inspection.setQueenPresence(QueenPresence.fromTextRepresentation(queenPresence.getValue()));
            inspection.setAmountOfChalkBrood(ChalkBroodAmount.fromTextRepresentation(amountOfChalkBrood.getValue()));
            inspection.setSwarmCells(SwarmCells.fromTextRepresentation(swarmCells.getValue()));
            inspection.setAngerLevel(AngerLevel.fromTextRepresentation(angerLevel.getValue()));

            List<ActionType> actionsPerformed = new ArrayList<>();

            if(addingQueenAction.isSelected()) actionsPerformed.add(ActionType.ADDING_QUEEN);
            if(removingQueenAction.isSelected()) actionsPerformed.add(ActionType.REMOVING_QUEEN);
            if(exportingBroodFramesAction.isSelected()) actionsPerformed.add(ActionType.EXPORTING_BROOD_FRAMES);
            if(harvestingHoneyAction.isSelected()) actionsPerformed.add(ActionType.HARVESTING_HONEY);

            inspection.setActionsList(actionsPerformed);

            if (editMode) {
                List<Inspection> inspectionsWithSameId = service.inspectionsProperty().stream().filter(el -> el.getId().equals(inspection.getId())).collect(Collectors.toList());
                service.inspectionsProperty().removeAll(inspectionsWithSameId);
            }
            service.addInspection(inspection);
            System.err.println(service.inspectionsProperty().size());
            close();

        });
        cancel.setOnAction(e -> close());
    }

    private void close() {
        hiveId.setText("");
        addingQueenAction.setSelected(false);
        removingQueenAction.setSelected(false);
        exportingBroodFramesAction.setSelected(false);
        harvestingHoneyAction.setSelected(false);

        stateOfBrood.setValue("Unknown");
        queenPresence.setValue("Unknown");
        amountOfChalkBrood.setValue("Unknown");
        swarmCells.setValue("Unknown");
        angerLevel.setValue("Unknown");
        modelInspection.getActiveInspection().set(null);
        AppViewManager.HIVES_VIEW.switchView();
    }
    
}
