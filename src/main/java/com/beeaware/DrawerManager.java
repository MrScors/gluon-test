package com.beeaware;

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

import static com.beeaware.Main.*;

public class DrawerManager {

    public static void buildDrawer(AppManager app) {
        NavigationDrawer drawer = app.getDrawer();
        
        NavigationDrawer.Header header = new NavigationDrawer.Header("Bee Aware",
                "Your Apiary Notes",
                new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/beeIcon.jpg"))));
        drawer.setHeader(header);
        
        final Item hivesItem = new ViewItem("Hives", MaterialDesignIcon.HOME.graphic(), HIVES_VIEW, ViewStackPolicy.SKIP);
        final Item notesItem = new ViewItem("Notes", MaterialDesignIcon.LIST.graphic(), NOTES_VIEW, ViewStackPolicy.SKIP);
        final Item settingsItem = new ViewItem("Settings", MaterialDesignIcon.SETTINGS.graphic(), SETTINGS_VIEW);
        drawer.getItems().addAll(hivesItem, notesItem, settingsItem);

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