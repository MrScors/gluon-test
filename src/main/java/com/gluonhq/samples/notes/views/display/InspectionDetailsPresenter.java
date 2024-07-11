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
package com.gluonhq.samples.notes.views.display;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.samples.notes.data.Inspection;
import com.gluonhq.samples.notes.data.enums.ActionType;
import com.gluonhq.samples.notes.data.model.ModelInspection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javax.inject.Inject;
import java.util.ResourceBundle;

public class InspectionDetailsPresenter {


    @FXML private Label uuid;
    @FXML private Label hiveIdLabel;
    @FXML private Label stateOfBroodLabel;
    @FXML private Label queenPresenceLabel;
    @FXML private Label amountOfChalkBroodLabel;
    @FXML private Label swarmCellsLabel;
    @FXML private Label angerLevelLabel;
    @FXML private ListView<String> actionsListView;
    @FXML private View inspectionDetails;
    @FXML private ResourceBundle resources;

    @Inject private ModelInspection modelInspection;


    public void initialize() {

        inspectionDetails.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText(resources.getString("appbar.title"));
            }
        });

        Inspection inspection = modelInspection.getActiveInspection().get();

        uuid.setText(inspection.getId().toString());
        hiveIdLabel.setText(Integer.toString(inspection.getHiveId()));
        stateOfBroodLabel.setText(inspection.getStateOfBrood().getTextRepresentation());
        queenPresenceLabel.setText(inspection.getQueenPresence().getTextRepresentation());
        amountOfChalkBroodLabel.setText(inspection.getAmountOfChalkBrood().getTextRepresentation());
        swarmCellsLabel.setText(inspection.getSwarmCells().getTextRepresentation());
        angerLevelLabel.setText(inspection.getAngerLevel().getTextRepresentation());

        actionsListView.getItems().clear();
        for (ActionType action : inspection.getActionsList()) {
            actionsListView.getItems().add(action.getTextRepresentation());
        }

    }

}
