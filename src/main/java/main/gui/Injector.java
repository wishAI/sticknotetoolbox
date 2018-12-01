package main.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Injector {

    public static void inject(Pane o, String name) {
        FXMLLoader loader = new FXMLLoader(o.getClass().getResource("/fxml/" + name + ".fxml"));
        loader.setRoot(o);
        loader.setController(o);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String css = o.getClass().getResource("/style/" + name + ".css").toExternalForm();
        String css2 = o.getClass().getResource("/style/" + "content.css").toExternalForm();
        o.getStylesheets().addAll(css, css2);
    }

}