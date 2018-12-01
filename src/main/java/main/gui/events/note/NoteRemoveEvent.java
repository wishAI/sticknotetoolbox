package main.gui.events.note;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.layout.Pane;


public class NoteRemoveEvent extends Event {

    public static final EventType<NoteRemoveEvent> NOTE_REMOVE_EVENT = new EventType<>(ANY, "NOTE_REMOVE_EVENT");


    private Pane pane;


    public NoteRemoveEvent(Pane pane) {
        super(NOTE_REMOVE_EVENT);
        this.pane = pane;
    }

    public Pane getPane() {
        return pane;
    }

}
