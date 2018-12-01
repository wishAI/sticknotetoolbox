package main.gui.notepane.content;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.gui.ClipboardManager;
import main.model.notes.StickyNote;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public abstract class ContentPane<N extends StickyNote, W extends Pane> extends AnchorPane implements Initializable {

    public static final Color LIGHT_GREY= new Color(0.8, 0.8, 0.8, 1);
    static final int WIDTH = 250;
    static final int HEIGHT = 230;


    protected W wrapper;
    protected AnchorPane root;

    protected N note;


    public ContentPane(N note) {
        this.note = note;
        root = this;
    }

    public N getNote() {
        return note;
    }

    public void showNotification(String info) {
        // make a new notification box
        VBox vBox = new VBox();
        vBox.prefWidthProperty().bind(this.widthProperty());
        vBox.setLayoutX(0);
        vBox.setLayoutY(170);
        vBox.setAlignment(Pos.CENTER);
        Label label = new Label(info);
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(150);
        label.setPrefHeight(30);
        label.getStyleClass().add("lb-notify");
        vBox.getChildren().add(label);

        // show the box for a while
        root.getChildren().add(vBox);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    root.getChildren().remove(vBox);
                    timer.cancel();
                    timer.purge();
                });
            }
        }, 700);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setWrapper();
        changeColor();
    }

    public void changeColor() {
    }

    protected void copyStringToClipBoard(Object o) {
        ClipboardManager.replace(o.toString());
        showNotification("copied");
    }

    protected void setWrapper() {
        this.wrapper = (W) root.lookup("#wrapper");
        bindWrapper(wrapper);
    }

    protected void bindWrapper(Region wrapper) {
        wrapper.prefWidthProperty().bind(this.widthProperty());
        wrapper.prefHeightProperty().bind(this.heightProperty());
    }

}

