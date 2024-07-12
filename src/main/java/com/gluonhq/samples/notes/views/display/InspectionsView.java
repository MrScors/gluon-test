package com.gluonhq.samples.notes.views.display;

import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.samples.notes.Main;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class InspectionsView {

    public View getView() {
        try {
            View view = FXMLLoader.load(Main.class.getResource("views/display/inspections.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            e.printStackTrace();
            return new View();
        }
    }
}
