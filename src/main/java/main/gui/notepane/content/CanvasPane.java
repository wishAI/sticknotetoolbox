package main.gui.notepane.content;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.gui.Injector;
import main.model.notes.CanvasNote;

import java.net.URL;
import java.util.ResourceBundle;

public class CanvasPane extends ContentPane<CanvasNote, Pane> {

    @FXML private Canvas canvas_main;


    public CanvasPane(CanvasNote note) {
        super(note);
        Injector.inject(this, "Canvas");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        canvas_main.setWidth(250);
        canvas_main.setHeight(220);

        var handler = new CanvasMouseHandler();
        canvas_main.setOnMouseDragged(handler);
        canvas_main.setOnMouseClicked(handler);
    }

    private class CanvasMouseHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            var gc = canvas_main.getGraphicsContext2D();
            double x = e.getX();
            double y = e.getY();

            if (e.getButton() == MouseButton.SECONDARY) {
                int r = 5;
                gc.clearRect(x - r, y - r, r * 2, r * 2);
            } else {
                int r = 1;
                gc.fillRect(x - r, y - r, r * 2, r * 2);
            }

            note.setCanvasContent(canvas_main.snapshot(null, null));
        }
    }

}
