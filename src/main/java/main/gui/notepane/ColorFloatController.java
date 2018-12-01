package main.gui.notepane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.gui.ColorManager;
import main.gui.events.note.NoteColorChangeEvent;
import main.model.notes.StickyNote;

import java.net.URL;
import java.util.ResourceBundle;


public class ColorFloatController implements Initializable {

    @FXML
    private HBox root;

    public ColorFloatController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set start location
        root.setLayoutX(0);
        root.setLayoutY(0);

        for(String str: StickyNote.COLORS) {
            Button btn = new Button();
            btn.prefWidthProperty().bind(root.widthProperty().divide(StickyNote.COLORS.length));
            btn.setPrefHeight(30);
            ColorManager.setColorToNode(btn, str);
            btn.getStyleClass().add("btn-color");
            btn.setOnAction(event -> {
                root.fireEvent(new NoteColorChangeEvent(str));
                swith();
            });
            root.getChildren().add(btn);
        }
    }

    Pane getRoot() {
        return root;
    }

    void swith() {
        this.root.toFront();
        this.root.setVisible(!this.root.isVisible());
    }

}
