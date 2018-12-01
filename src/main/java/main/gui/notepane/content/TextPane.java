package main.gui.notepane.content;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import main.gui.Injector;
import main.gui.events.CommandEvent;
import main.gui.handlers.ContentPushTarget;
import main.model.factory.StickyNoteFactory;
import main.model.notes.ColorPickerNote;
import main.model.notes.TextNote;

import java.net.URL;
import java.util.ResourceBundle;


public class TextPane extends ContentPane<TextNote, Pane> implements ContentPushTarget {

    @FXML
    Pane node;
    @FXML
    TextArea textArea;


    public TextPane(TextNote note) {
        super(note);
        Injector.inject(this, "Text");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setWrapper();

        textArea.setWrapText(true);
        textArea.prefHeightProperty().bind(this.heightProperty().subtract(10));
        textArea.prefWidthProperty().bind(this.widthProperty());
        textArea.setText(note.getContent());
        textArea.textProperty().addListener((observable, oldValue, newValue) -> updateContent(newValue));
    }

    @Override
    public void onTextPush(String str) {
        if (ColorPickerNote.isHexCode(str.trim()) && note.getContent().length() == 0) {
            // switch to color picker note
            ColorPickerNote colorPickerNote = (ColorPickerNote) StickyNoteFactory.getStickyNote("ColorPicker", note);
            colorPickerNote.setHexValue(str.trim());
            root.fireEvent(new CommandEvent("switch", colorPickerNote));
        } else {
            updateContent(note.getContent() + " " + str);
        }
    }

    private void updateContent(String str) {
        note.setContent(str);
        if (!note.getContent().equals(textArea.getText()))
            textArea.setText(str);
    }

}
