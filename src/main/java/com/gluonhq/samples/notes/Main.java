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
package com.gluonhq.samples.notes;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.gluonhq.samples.notes.views.DrawerManager;
import com.gluonhq.samples.notes.views.display.HivesView;
import com.gluonhq.samples.notes.views.display.InspectionDetailsView;
import com.gluonhq.samples.notes.views.display.InspectionsView;
import com.gluonhq.samples.notes.views.display.NotesView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

public class Main extends Application {

    public static final String POPUP_FILTER_NOTES = "Filter Notes";
    public static final String POPUP_FILTER_HIVES = "Filter Hives";
    public static final String HIVES_VIEW = HOME_VIEW;
    public static final String NOTES_VIEW = "Notes";
    public static final String INSPECTIONS_VIEW = "InspectionsView";
    public static final String INSPECTION_DETAILS_VIEW = "InspectionDetailsView";

    private final AppManager appManager = AppManager.initialize(this::postInit);

    @Override
    public void init() {
        appManager.addViewFactory(HIVES_VIEW, () -> new HivesView().getView());
        appManager.addViewFactory(NOTES_VIEW, () -> new NotesView().getView());
        appManager.addViewFactory(INSPECTIONS_VIEW, () -> new InspectionsView().getView());
        appManager.addViewFactory(INSPECTION_DETAILS_VIEW, () -> new InspectionDetailsView().getView());
//        appManager.addViewFactory(LIST_ACTION_VIEW, () -> new ListActionView().getView());
//        appManager.addViewFactory(LIST_OBSERVATION_VIEW, () -> new ListObservationView().getView());

        DrawerManager.buildDrawer(appManager);
    }

    @Override
    public void start(Stage stage) {
        appManager.start(stage);
    }

    private void postInit(Scene scene) {
        Swatch.LIGHT_GREEN.assignTo(scene);

        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(Main.class.getResourceAsStream("/beeIcon.jpg")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
