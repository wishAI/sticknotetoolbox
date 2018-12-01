package main.gui.notepane;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import main.gui.notepane.content.ContentPane;
import main.model.notes.StickyNote;
import main.model.notes.View;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class NotePaneFactory {

    public static NotePaneController makeNotePane(StickyNote note) {
        FXMLLoader loader = null;
        Pane pane = null;
        try {
            loader = new FXMLLoader(NotePaneController.class.getResource("/fxml/NotePane.fxml"));
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert pane != null;

        NotePaneController controller = loader.<NotePaneController>getController();
        assert controller != null;
        controller.setContentPane(makeContentPane(note));

        return controller;
    }

    public static ContentPane makeContentPane(StickyNote note) {
        Class<?> c = note.getClass().getAnnotation(View.class).value();
        ContentPane content = null;
        try {
            content = (ContentPane) c.getConstructor(note.getClass()).newInstance(note);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        assert content != null;

        return content;
    }

}