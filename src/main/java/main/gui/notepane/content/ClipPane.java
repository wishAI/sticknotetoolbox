package main.gui.notepane.content;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.gui.ClipboardManager;
import main.gui.ColorManager;
import main.gui.Injector;
import main.gui.handlers.ContentPushTarget;
import main.model.notes.ClipNote;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class ClipPane extends ListContentPane<ClipNote, Pane, VBox> implements ContentPushTarget {

    @FXML private Button btn_clip;


    public ClipPane(ClipNote note) {
        super(note);
        Injector.inject(this, "Clip");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        showListPane();

        // setup clip button
        btn_clip.setPrefSize(50, 50);
        btn_clip.setLayoutX(190);
        btn_clip.setLayoutY(160);
        btn_clip.toFront();
    }

    @Override
    protected void onItemClick(Map<String, Object> item) {
        Object o = item.get("clip");
        if (o instanceof File) {
            // open file
            try {
                Desktop.getDesktop().open((File) o);
            } catch (IOException e) {
                showNotification("Open failed. ");
            }
        } else if (ClipNote.isUrl(o.toString())) {
            // open link
            try {
                Desktop.getDesktop().browse(new URI((String) o));
            } catch (IOException | URISyntaxException e) {
                showNotification("Open failed. ");
            }
        } else {
            copyStringToClipBoard(o.toString());
        }
    }

    @Override
    public void changeColor() {
        super.changeColor();
        ColorManager.setColorToNode(btn_clip, note.getColor());
        ColorManager.setHoverColorToNode(btn_clip, note.getColor(), new Color(0, 0, 0, 0.1));
    }

    @Override
    protected Node makeItemBox(Map<String, Object> item) {
        var box = makeItemBox(HBox.class, 50);
        var clip = makeItemLabel(item, "clip");
        box.getChildren().add(clip);
        return box;
    }

    @FXML
    public void handleClip() {
        if (ClipboardManager.pushContentTo(this))
            showListPane();
        else
            showNotification("Clipboard is empty. ");
    }

    @Override
    public void onTextPush(String text) {
        note.addClip(text);
        showListPane();
    }

    @Override
    public void onFilePush(File file) {
        note.addClip(file);
        showListPane();
    }

}
