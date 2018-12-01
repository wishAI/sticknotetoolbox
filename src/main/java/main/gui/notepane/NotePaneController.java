package main.gui.notepane;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import main.gui.ColorManager;
import main.gui.events.note.*;
import main.gui.handlers.ContentPushTarget;
import main.gui.notepane.content.ContentPane;
import main.gui.handlers.DragMoveHandler;
import main.gui.notepane.content.ListContentPane;
import main.gui.events.CommandEvent;
import main.gui.handlers.ContentDragInHandler;
import main.model.notes.RecordableStickNote;
import main.model.notes.StickyNote;
import main.service.StoreService;

import java.net.URL;
import java.util.ResourceBundle;


public class NotePaneController implements Initializable {

    @FXML private Pane wrapper_note;
    @FXML private AnchorPane wrapper_floating;
    @FXML private CommandBarController commandBarController;
    @FXML private ColorFloatController colorFloatController;
    @FXML private BorderPane root;
    private ContentPane pane_content;
    private CommandHandler commandHandler;

    private StickyNote note;


    public NotePaneController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wrapper_note.prefHeightProperty().bind(root.heightProperty());
        wrapper_note.prefWidthProperty().bind(root.widthProperty());

        wrapper_floating.setPrefSize(0, 0);
        wrapper_floating.toFront();
        wrapper_floating.setLayoutX(0);
        wrapper_floating.setLayoutY(0);

        Pane bar_command = commandBarController.getRoot();
        bar_command.prefWidthProperty().bind(root.widthProperty());
        bar_command.setPrefHeight(30);
        commandHandler = new CommandHandler();
        bar_command.addEventHandler(CommandEvent.COMMAND, commandHandler);
        DragMoveHandler.addSourceTargetPair(bar_command, root);

        Pane float_color = colorFloatController.getRoot();
        float_color.prefWidthProperty().bind(root.widthProperty());
        float_color.addEventHandler(NoteColorChangeEvent.NOTE_COLOR_CHANGE, event -> changeColor(event.getColor()));
    }

    public void onDestroy() {
        note.removeFromBoard();
    }

    void setContentPane(ContentPane pane) {
        this.note = pane.getNote();

        // remove previous content
        if (this.pane_content != null) {
            wrapper_note.getChildren().remove(this.pane_content);
        }

        this.pane_content = pane;
        pane.prefHeightProperty().bind(root.heightProperty().subtract(30));
        pane.prefWidthProperty().bind(root.widthProperty());

        this.wrapper_note.getChildren().add(pane);

        this.commandBarController.updateCmdButtons(note);
        changeColor(note.getColor());

        pane.addEventHandler(CommandEvent.COMMAND, commandHandler);
        if (pane instanceof ContentPushTarget)
            ContentDragInHandler.setSource(wrapper_note, (ContentPushTarget) pane);
    }

    public void showNotification(String info) {
        pane_content.showNotification(info);
    }

    public Pane getRoot() {
        return root;
    }

    public Pane getCommandBar() {
        return commandBarController.getRoot();
    }

    public class CommandHandler implements EventHandler<CommandEvent> {
        @Override
        public void handle(CommandEvent event) {
            switch(event.getCommand()) {
                case "color":
                    colorFloatController.swith();
                    break;
                case "add":
                    root.fireEvent(new NoteAddEvent());
                    break;
                case "addFunction":
                    root.fireEvent(new NoteAddEvent((String) event.getArg()));
                    break;
                case "switch":
                    note.replace((StickyNote) event.getArg());
                    note = (StickyNote) event.getArg();
                    ContentPane pane = NotePaneFactory.makeContentPane(note);
                    setContentPane(pane);
                    break;
                case "items":
                    if(pane_content instanceof ListContentPane)
                        ((ListContentPane) pane_content).switchListPane();
                    break;
                case "record":
                    assert note != null;
                    if(!(note instanceof RecordableStickNote)) {
                        throw new RuntimeException("The note is not recordable. ");
                    }
                    ((RecordableStickNote) note).record();
                    showNotification("Recorded");
                    break;
                case "save":
                    root.fireEvent(new NoteSaveEvent());
                    break;
                case "close":
                    onDestroy();
                    root.fireEvent(new NoteRemoveEvent(root));
                    break;
                case "pin":
                    root.fireEvent(new NotePinEvent(NotePaneController.this));
                    break;
            }
        }
    }

    private void changeColor(String color) {
        if (note != null)
            note.setColor(color);
        ColorManager.setColorToNode(commandBarController.getRoot(), color);
        ColorManager.setLightColorToNode(wrapper_note, color);
        if (pane_content != null)
            pane_content.changeColor();
    }

}
