package main.gui.events.note;

import javafx.event.Event;
import javafx.event.EventType;

public class NoteColorChangeEvent extends Event {

    public static final EventType<NoteColorChangeEvent> NOTE_COLOR_CHANGE = new EventType<>(ANY, "NOTE_COLOR_CHANGE");


    private String color;


    public NoteColorChangeEvent(String color) {
        super(NOTE_COLOR_CHANGE);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
