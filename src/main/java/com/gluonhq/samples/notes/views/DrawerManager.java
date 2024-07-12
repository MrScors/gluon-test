package com.gluonhq.samples.notes.views;

import com.gluonhq.attach.lifecycle.LifecycleService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.attach.util.Services;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.scene.image.Image;

import static com.gluonhq.samples.notes.Main.*;

public class DrawerManager {

    public static void buildDrawer(AppManager app) {
        NavigationDrawer drawer = app.getDrawer();
        
        NavigationDrawer.Header header = new NavigationDrawer.Header("Bee Aware",
                "Your Apiary Notes",
                new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/beeIcon.jpg"))));
        drawer.setHeader(header);
        
        final Item hivesView = new ViewItem("Hives", MaterialDesignIcon.HOME.graphic(), HIVES_VIEW, ViewStackPolicy.SKIP);
        final Item notesView = new ViewItem("Notes", MaterialDesignIcon.LIST.graphic(), NOTES_VIEW, ViewStackPolicy.SKIP);
        final Item inspectionsView = new ViewItem("Inspections", MaterialDesignIcon.CHECK.graphic(), INSPECTIONS_VIEW, ViewStackPolicy.SKIP);
        drawer.getItems().addAll(hivesView, notesView, inspectionsView);

        if (Platform.isDesktop()) {
            final Item quitItem = new Item("Quit", MaterialDesignIcon.EXIT_TO_APP.graphic());
            quitItem.selectedProperty().addListener((obs, ov, nv) -> {
                if (nv) {
                    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
                }
            });
            drawer.getItems().add(quitItem);
        }
    }
}