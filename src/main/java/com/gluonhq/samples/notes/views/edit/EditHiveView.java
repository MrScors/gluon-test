package com.gluonhq.samples.notes.views.edit;

import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.samples.notes.Main;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class EditHiveView {

    public View getView() {
        try {
            View view = FXMLLoader.load(Main.class.getResource("views/edit/edithive.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            e.printStackTrace();
            return new View();
        }
    }
}
