package main.gui;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.gui.events.note.NoteAddEvent;
import main.gui.events.note.NotePinEvent;
import main.gui.events.note.NoteRemoveEvent;
import main.gui.events.note.NoteSaveEvent;
import main.gui.handlers.DragMoveHandler;
import main.gui.notepane.NotePaneController;
import main.gui.notepane.NotePaneFactory;
import main.model.Board;
import main.model.factory.StickyNoteFactory;
import main.model.notes.CommandNote;
import main.model.notes.StickyNote;
import main.service.StoreService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;


public class MainApplication extends Application {

    private Pane root;
    private Collection<Stage> noteWindows;

    private Board board;

    private StoreService storeService;

    private double noteX;
    private double noteY;


    public MainApplication() {
        super();
        storeService = StoreService.getInstance();
        noteWindows = new HashSet<>();
        noteX = 50;
        noteY = 50;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        storeService = StoreService.getInstance();

        this.board = new Board();
        storeService.loadBoard(board);
        CommandNote.setTarget(board);

        for(int i = 0; i < board.getNotes().size(); i ++) {
            addNotePane(board.getNotes().get(i));
        }
    }

    private void addNotePane(String type) {
        StickyNote note = StickyNoteFactory.getStickyNote(type, this.board);
        addNotePane(note);
    }

    private void addNotePane(StickyNote note) {
        assert note != null;

        // create a new float window to hold the pane
        Stage window = new Stage();
        window.initStyle(StageStyle.UNDECORATED);
        window.setX(noteX);
        window.setY(noteY);

        // create pane for the note
        NotePaneController controller = NotePaneFactory.makeNotePane(note);
        Pane pane = controller.getRoot();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        pane.addEventHandler(NoteAddEvent.NOTE_ADD_EVENT, e -> addNotePane(e.getType()));
        pane.addEventHandler(NoteRemoveEvent.NOTE_REMOVE_EVENT, e -> Objects.requireNonNull(findWindowByNotePane(e.getPane())).close());
        pane.addEventHandler(NotePinEvent.NOTE_PIN_EVENT, e -> {
            NotePaneController c = e.getController();
            Stage win = findWindowByNotePane(c.getRoot());
            assert win != null;
            win.setAlwaysOnTop(!win.isAlwaysOnTop());
            if (win.isAlwaysOnTop())
                c.showNotification("Pinned");
            else
                c.showNotification("Unpinned");
        });
        pane.addEventHandler(NoteSaveEvent.NOTE_SAVE_EVENT, e -> {
            storeService.saveNotes(board);
        });

        DragMoveHandler.addSourceTargetPair(controller.getCommandBar(), window, DragMoveHandler.MODE_SCREEN);

        // add pane to stage
        Scene scene = new Scene(pane, 250,  250);
        window.setScene(scene);
        window.show();
        noteWindows.add(window);

        if(noteX > 600) {
            noteX = 400;
            noteY = 50;
        } else {
            noteX += 100;
            noteY += 100;
        }
    }

    private Stage findWindowByNotePane(Pane pane) {
        for (var win : noteWindows) {
            Node p = win.getScene().getRoot();
            if (p == pane)
                return win;
        }

        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
