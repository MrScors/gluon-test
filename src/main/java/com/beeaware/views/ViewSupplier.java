package com.beeaware.views;

import com.beeaware.Main;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ViewSupplier {

    public static View getView(String location) {
        try {
            View view = FXMLLoader.load(Main.class.getResource(location));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            e.printStackTrace();
            return new View();
        }
    }
}
