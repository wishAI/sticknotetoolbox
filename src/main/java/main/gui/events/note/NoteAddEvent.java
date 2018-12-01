package main.gui.events.note;

import javafx.event.Event;
import javafx.event.EventType;


public class NoteAddEvent extends Event {

    public static final EventType<NoteAddEvent> NOTE_ADD_EVENT = new EventType<>(ANY, "NOTE_ADD_EVENT");


    private String type;


    public NoteAddEvent() {
        this("Text");
    }

    public NoteAddEvent(String type) {
        super(NOTE_ADD_EVENT);
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
