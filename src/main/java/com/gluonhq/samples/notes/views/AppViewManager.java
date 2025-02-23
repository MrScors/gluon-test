/*
 * Copyright (c) 2017, 2021, Gluon
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
package com.gluonhq.samples.notes.views;

import com.gluonhq.charm.glisten.afterburner.AppView;
import com.gluonhq.charm.glisten.afterburner.AppViewRegistry;
import com.gluonhq.charm.glisten.afterburner.Utils;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.samples.notes.views.display.HivesPresenter;
//import com.gluonhq.samples.notes.views.display.InspectionDetailsPresenter;
//import com.gluonhq.samples.notes.views.display.InspectionsPresenter;
import com.gluonhq.samples.notes.views.display.InspectionDetailsPresenter;
import com.gluonhq.samples.notes.views.display.InspectionsPresenter;
import com.gluonhq.samples.notes.views.display.NotesPresenter;
import com.gluonhq.samples.notes.views.edit.EditHivePresenter;
//import com.gluonhq.samples.notes.views.edit.EditInspectionPresenter;
import com.gluonhq.samples.notes.views.edit.EditInspectionPresenter;
import com.gluonhq.samples.notes.views.edit.EditNotePresenter;
import javafx.scene.image.Image;

import java.util.Locale;

import static com.gluonhq.charm.glisten.afterburner.AppView.Flag.HOME_VIEW;
import static com.gluonhq.charm.glisten.afterburner.AppView.Flag.SHOW_IN_DRAWER;
import static com.gluonhq.charm.glisten.afterburner.AppView.Flag.SKIP_VIEW_STACK;

public class AppViewManager {

    private static final AppViewRegistry REGISTRY = new AppViewRegistry();

//    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.gluonhq.samples.notes.drawer");

    public static final AppView HIVES_VIEW = view("Hives", HivesPresenter.class, MaterialDesignIcon.HOME, SHOW_IN_DRAWER, HOME_VIEW, SKIP_VIEW_STACK);
    public static final AppView NOTES_VIEW = view("Notes", NotesPresenter.class, MaterialDesignIcon.LIST, SHOW_IN_DRAWER);
    public static final AppView INSPECTIONS_VIEW = view("Inspections", InspectionsPresenter.class, MaterialDesignIcon.CHECK, SHOW_IN_DRAWER);
    public static final AppView INSPECTION_DETAILS_VIEW = view("Inspection Details", InspectionDetailsPresenter.class, MaterialDesignIcon.CHECK, SKIP_VIEW_STACK);
    public static final AppView EDIT_HIVE_VIEW = view("Edit Hive", EditHivePresenter.class, MaterialDesignIcon.EDIT, SKIP_VIEW_STACK);
    public static final AppView EDIT_INSPECTION_VIEW = view("Edit Inspection", EditInspectionPresenter.class, MaterialDesignIcon.EDIT, SKIP_VIEW_STACK);
    public static final AppView EDIT_NOTE_VIEW = view("Edit Note", EditNotePresenter.class, MaterialDesignIcon.EDIT, SKIP_VIEW_STACK);
    public static final AppView SETTINGS_VIEW = view("Settings", SettingsPresenter.class, MaterialDesignIcon.SETTINGS, SHOW_IN_DRAWER);
    
    private static AppView view(String title, Class<?> presenterClass, MaterialDesignIcon menuIcon, AppView.Flag... flags ) {
        return REGISTRY.createView(name(presenterClass), title, presenterClass, menuIcon, flags);
    }

    private static String name(Class<?> presenterClass) {
        return presenterClass.getSimpleName().toUpperCase(Locale.ROOT).replace("PRESENTER", "");
    }
    
    public static void registerViewsAndDrawer() {
        for (AppView view : REGISTRY.getViews()) {
            view.registerView();
        }

        NavigationDrawer.Header header = new NavigationDrawer.Header("Bee Aware",
                "Apiary Notes",
                new Avatar(21, new Image(AppViewManager.class.getResourceAsStream("/beeIcon.jpg"))));

        Utils.buildDrawer(AppManager.getInstance().getDrawer(), header, REGISTRY.getViews());
    }
}
