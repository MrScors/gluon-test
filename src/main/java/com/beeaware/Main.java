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
package com.beeaware;

import com.beeaware.views.ViewSupplier;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.beeaware.UtilsCustom.*;
import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

public class Main extends Application {

    public static final String HIVES_VIEW = HOME_VIEW;
    public static final String NOTES_VIEW = "Notes";
    public static final String SETTINGS_VIEW = "Settings";
    public static final String EDIT_HIVE_VIEW = "Edit Hives";
    public static final String EDIT_NOTE_VIEW = "Edit Notes";
    public static final String POPUP_FILTER_HIVES = "Filter Hives";
    public static final String POPUP_FILTER_NOTES = "Filter Notes";
    private final AppManager appManager = AppManager.initialize(this::postInit);

    @Override
    public void init() {

        appManager.addViewFactory(HIVES_VIEW, () -> ViewSupplier.getView(HIVES_VIEW_LOCATION));
        appManager.addViewFactory(NOTES_VIEW, () -> ViewSupplier.getView(NOTES_VIEW_LOCATION));
        appManager.addViewFactory(EDIT_HIVE_VIEW, () -> ViewSupplier.getView(EDIT_HIVE_VIEW_LOCATION));
        appManager.addViewFactory(EDIT_NOTE_VIEW, () -> ViewSupplier.getView(EDIT_NOTE_VIEW_LOCATION));
        appManager.addViewFactory(POPUP_FILTER_HIVES, () -> ViewSupplier.getView(POPUP_FILTER_HIVES_LOCATION));
        appManager.addViewFactory(POPUP_FILTER_NOTES, () -> ViewSupplier.getView(POPUP_FILTER_NOTES_LOCATION));
        appManager.addViewFactory(SETTINGS_VIEW, () -> ViewSupplier.getView(SETTINGS_VIEW_LOCATION));

        DrawerManager.buildDrawer(appManager);
    }

    @Override
    public void start(Stage stage) {
        appManager.start(stage);
    }

    private void postInit(Scene scene) {
        Swatch.LIGHT_GREEN.assignTo(scene);

        scene.getStylesheets().add(Main.class.getResource("/com/beeaware/views/style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(Main.class.getResourceAsStream("/beeIcon.jpg")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
