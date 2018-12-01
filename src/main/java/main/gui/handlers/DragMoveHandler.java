package main.gui.handlers;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class DragMoveHandler {

    public static final String MODE_SCREEN = "screen";
    public static final String MODE_STAGE = "stage";


    private double dragX;
    private double dragY;
    private double iniX;
    private double iniY;
    private Object target;
    private String mode;


    private DragMoveHandler(Node source, Object target, String mode) {
        this.target = target;
        this.mode = mode;
        dragX = 0;
        dragY = 0;
        iniX = 0;
        iniY = 0;

        source.setOnMousePressed(new PressHandler());
        source.setOnMouseDragged(new DragHandler());
    }

    public static void addSourceTargetPair(Node proto, Object target) {
        new DragMoveHandler(proto, target, MODE_STAGE);
    }

    public static void addSourceTargetPair(Node proto, Object target, String mode) {
        new DragMoveHandler(proto, target ,mode);
    }

    private class PressHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent e) {
            if (mode.equals(MODE_STAGE)) {
                Node n = (Node) target;
                iniX = n.getLayoutX();
                iniY = n.getLayoutY();
                dragX = e.getSceneX();
                dragY = e.getSceneY();
            } else {
                Stage s = (Stage) target;
                iniX = s.getX();
                iniY = s.getY();
                dragX = e.getScreenX();
                dragY = e.getScreenY();
            }
        }

    }

    private class DragHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent e) {
            if (mode.equals(MODE_STAGE)) {
                Node n = (Node) target;
                n.setLayoutX(iniX + e.getSceneX() - dragX);
                n.setLayoutY(iniY + e.getSceneY() - dragY);
            } else {
                Stage s = (Stage) target;
                s.setX(iniX + e.getScreenX() - dragX);
                s.setY(iniY + e.getScreenY() - dragY);
            }
        }

    }

}
